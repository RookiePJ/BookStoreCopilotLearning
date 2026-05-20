#!/usr/bin/env bash
# Verify that application-prod.properties does not contain DEBUG logging entries
set -euo pipefail

# Allow overriding the file path for testing: first arg is optional
FILE="${1:-src/main/resources/application-prod.properties}"

if [[ ! -f "$FILE" ]]; then
  echo "No $FILE present - OK"
  exit 0
fi

# Fail if any property is assigned DEBUG (e.g. logging.level.xyz=DEBUG)
if grep -qE '=\s*DEBUG' "$FILE"; then
  echo "ERROR: $FILE contains DEBUG logging level which must not be committed to main." >&2
  echo "Remove DEBUG entries from $FILE or do not target the main branch with this change." >&2
  exit 1
fi

echo "$FILE does not contain DEBUG entries."
exit 0

