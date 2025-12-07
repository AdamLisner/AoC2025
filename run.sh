#!/bin/bash
# Run the AoC solution directly (no SBT startup overhead)

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CLASSPATH_FILE="$SCRIPT_DIR/.classpath_cache"

# Function to generate classpath
generate_classpath() {
    cd "$SCRIPT_DIR"
    # Use sbt to export the classpath
    local CP=$(sbt -error "export runtime:fullClasspath" 2>/dev/null | tail -1)
    if [ -n "$CP" ]; then
        echo "$CP"
    else
        # Fallback: construct classpath manually from standard locations
        local APP_CLASSES="$SCRIPT_DIR/target/scala-3.3.1/classes"
        local IVY2="$HOME/.ivy2/cache"
        local COURSIER="$HOME/.cache/coursier/v1/https/repo1.maven.org/maven2"

        # Find Scala 3 library
        local SCALA3_LIB=$(find "$COURSIER/org/scala-lang" "$IVY2/org.scala-lang" -name "scala3-library_3*.jar" 2>/dev/null | head -1)
        local SCALA_LIB=$(find "$COURSIER/org/scala-lang" "$IVY2/org.scala-lang" -name "scala-library-2*.jar" 2>/dev/null | head -1)

        echo "$APP_CLASSES:$SCALA3_LIB:$SCALA_LIB"
    fi
}

# Generate classpath cache if it doesn't exist or is older than build.sbt
if [ ! -f "$CLASSPATH_FILE" ] || [ "$SCRIPT_DIR/build.sbt" -nt "$CLASSPATH_FILE" ]; then
    echo "Generating classpath cache..." >&2
    generate_classpath > "$CLASSPATH_FILE"
fi

CLASSPATH=$(cat "$CLASSPATH_FILE")

java -cp "$CLASSPATH" Main

