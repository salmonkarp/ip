# TaskBot

## Quickstart
1. Head to [the site of my fork](https://github.com/salmonkarp/ip).
2. Go to the Releases section
3. Download the .jar file
4. Put it into an empty folder, and run by clicking it or running `java -jar "taskBot.jar"` in a terminal.
5. Refer to the features section below for usage.

Alternatively, you can also clone the repo and build it yourself:
```
git clone https://github.com/salmonkarp/ip.git
```
![Screenshot of the image](./Ui.png)

# Features

## Adding a todo: `todo`
Adds a todo task to the list.

Format: `todo DESCRIPTION`

Example: `todo read book`

## Adding a deadline: `deadline`
Adds a deadline task to the list.

Format: `deadline DESCRIPTION / TIME`

Examples: 
- `deadline return book / 2025-09-15 18:00`
- `deadline submit report / 2025-09-15`
- `deadline module homework / Jun 13 2025`
- `deadline send mail / Jun 13 2025 09:30`

## Adding an event: `event`
Adds an event task to the list.

Format: `event DESCRIPTION / START_TIME / END_TIME`

Examples:
- `event project meeting / 2025-09-15 14:00 / 2025-09-15 16:00`
- `event teaching session / 2025-09-15 / 2025-09-15`
- `event lecture / Jun 13 2025 14:00 / Jun 13 2025 16:00`
- `event tutorial / Jun 13 2025 / Jun 14 2025`

## Listing all tasks: `list`
Lists all tasks in the list.

## Marking a task as done: `mark`
Marks a task as done.

Format: `mark INDEX`

Example: `mark 2`

## Unmarking a task as not done: `unmark`
Unmarks a task as not done.

Format: `unmark INDEX`

Example: `unmark 2`

## Deleting a task: `delete`
Deletes a task from the list.

Format: `delete INDEX`

Example: `delete 2`

## Finding tasks by keyword: `find`
Finds tasks that contain the given keyword.

Format: `find KEYWORD`

Example: `find book`

## Sorting tasks by type: `sort`
Sorts tasks by specific parameters.

Format: `sort [PARAMETER]`

Parameters:
- `alphabetical`: Sorts tasks in alphabetical order by description.
- `time_created`: Sorts tasks by the time they were created (oldest to newest).
- `deadline`: Sorts tasks by their deadlines (earliest to latest). Tasks without deadlines are listed at the end.
- `is_done`: Sorts tasks by their completion status (not done first, then done).

Example: `sort deadline`

## Saving tasks to file: `save`
Saves the current list of tasks to a local file.

## Exiting the program: `bye`
Exits the program.

> This is a project for CS2103T 25/26 S1.

_Accurate as of 15/9/2025_

