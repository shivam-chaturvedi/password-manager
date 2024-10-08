# Password Manager CLI

A simple and secure Command Line Interface (CLI) Password Manager built in Java that allows you to store, retrieve, and manage your passwords. This password manager ensures your credentials are kept safe while offering flexibility in generating and retrieving passwords with ease.

## Features

- **Add New Password**: Add new passwords for different user IDs with either custom or auto-generated strong passwords.
- **View Saved Passwords**: Retrieve and display all stored passwords or specific ones for a given platform.
- **Auto-Password Generation**: Automatically generates secure passwords if none are provided by the user.
- **Clipboard Integration**: Automatically copies passwords to the clipboard when fetched for a specific user.
- **Customizable Default Password Length**: The default password length is 15, but you can configure it as needed.

## Commands

### 1. Add New Password

To add a new password for a user ID, you can use:

```bash
pm -add
```

This will prompt for a `UserID` and `Password`. If no password is provided (just press `Enter`), an auto-generated strong password of the default length (15) will be used.

#### Example:

```bash
pm -add
```

You will be prompted as follows:

```bash
Enter UserId( email or username ) : example_user
Set Password For username ( Press Enter To Auto Generate ) : <press Enter>
```

This will generate a password and store it for `example_user`.

### 2. Set Default Password Length

You can change the default password length by specifying a number using the `-dpl` flag:

```bash
pm -add -dpl <NUMBER>
```

This will set the default password length for all auto-generated passwords to `<NUMBER>` characters.

#### Example:

```bash
pm -add -dpl 20
```

This will generate a new password of length 20 if no password is provided.

### 3. Show All Stored Passwords

To view all saved `UserID` and `Password` entries:

```bash
pm -show
```

This will list all the user IDs and their corresponding passwords.

### 4. Show Password for Specific Platform (e.g., YouTube)

To show the password for a specific platform or user ID and automatically copy it to the clipboard:

```bash
pm -show <UserID>
```

#### Example:

```bash
pm -show youtube
```

This will display the password for the `youtube` account and copy it to your clipboard for easy pasting.

## Installation

1. Clone the repository:

```bash
git clone https://github.com/yourusername/passwordmanager.git
```

2. Build the application using Maven:

```bash
mvn clean package
```

3. Run the application using the generated executable or JAR file.

## Usage

Once the application is built, you can run it directly from the command line by using the `pm` command, followed by the available options and flags.

#### Example:

```bash
pm -add
pm -show
pm -add -dpl 20
pm -show youtube
```

## How It Works

- Passwords are stored securely in a local file.
- Auto-generated passwords are complex and secure by default.
- Password retrieval is done in a way that ensures passwords are copied to your clipboard for convenience.

## Contribution

Feel free to contribute to this project by submitting a pull request or opening an issue!

## Download Link
[Password Manager](https://shivamchaturvedi.vercel.app/projects#5)
