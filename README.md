# Streaming Service Database
This application simulates interactions between a streaming service application and MySQL. It provides the functionality of loading up a database and populating it with data, as well as making queries about said database either through default options or writing your own SQL queries.

## Prerequisite
Set up a connection in MySQL at localhost 3306 with a username and password

## MySQL Login
When starting the app, there will be a prompt to log in to the MySQL connection at the default localhost 3306. There is also an option to initialized the database if it has not already been. Enter the username and password to log in.
- If the option to initialize the database is chosen, the `setup.sql` file will be run
- Queries in `queries.sql` are loaded as default queries

## Making Queries
- Default queries can be chosen and run by clicking the Run button
- Changes can be made in the query window on the left (or new queries can be written there)
- Query output will be shown in the right window
