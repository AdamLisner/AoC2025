#!/bin/bash
# Performance benchmark script for AoC solution
# This script ensures code is compiled and then runs hyperfine with proper warmup

echo "=== AoC Performance Benchmark ==="
echo ""

# Ensure code is compiled
echo "📦 Ensuring code is compiled..."
sbt compile > /dev/null 2>&1
echo "✓ Compilation complete"
echo ""

# Run benchmark without startup overhead
echo "🔥 Running Hyperfine benchmark (excluding JVM startup overhead)..."
echo "   - Warmup runs: 5"
echo "   - Benchmark runs: 20"
echo ""

hyperfine \
  --warmup 5 \
  --runs 20 \
  --style full \
  --export-markdown benchmark-results.md \
  './run.sh'

echo ""
echo "✓ Benchmark complete!"
echo "📊 Results saved to: benchmark-results.md"

