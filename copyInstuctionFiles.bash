#!/bin/bash
# This script is used to copy the instruction files from this project into a new project

# Echo a message indicating that the script is running
echo "Script to copy GitHub Copilot instruction files and AGENTS.md into a new project"

#Take an argument for the new project name
if [ -z "$1" ]; then
  echo "Please provide the new projects full directory as an argument."
  echo "Usage: $0 <new_project_directory>"
  exit 1
fi

NEW_PROJECT_DIRECTORY="$1"

# Define the source directory for the instruction files
SOURCE_INSTRUCTION_DIR="./.github/instructions"
# Define the destination directory for the instruction files in the new project
DEST_INSTRUCTIONS_DIR="$NEW_PROJECT_DIRECTORY/.github/instructions"
# Create the destination directory if it doesn't exist

#Define the source file for skills directory
SOURCE_SKILLS_DIR="./.github/skills"
#Define the destination directory for the skills directory in the new project
DEST_SKILLS_DIR="$NEW_PROJECT_DIRECTORY/.github/skills"

# Create the destination directory for skills/instructions if doesn't exist
mkdir -p "$DEST_INSTRUCTIONS_DIR"
mkdir -p "$DEST_SKILLS_DIR"

# Copy the AGENTS.md file to the new project directory
cp "./AGENTS.md" "$NEW_PROJECT_DIRECTORY/AGENTS.md"

#Rsync the instruction files to the new project directory
rsync -av "$SOURCE_INSTRUCTION_DIR/" "$DEST_INSTRUCTIONS_DIR/"

#Rsync the skills directory to the new project directory
rsync -av "$SOURCE_SKILLS_DIR/" "$DEST_SKILLS_DIR/"

#Echo a message indicating that the files have been copied
echo "--> Instruction files and AGENTS.md have been copied to the new project directory: $NEW_PROJECT_DIRECTORY"

#Show the contents of the new project directory Agents.md and the instruction_files directory
echo "--> Contents of the new project directory: $NEW_PROJECT_DIRECTORY"
ls -l "$NEW_PROJECT_DIRECTORY/AGENTS.md"
ls -l "$DEST_INSTRUCTIONS_DIR"
ls -l "$DEST_SKILLS_DIR"



