# How to run Netbeans modules in Intellij

## Configuration
* Go to edit configurations and add a new one
* Add a maven configuration
* Choose the application folder as the working directory
* Add the following parameters in the command line:
`clean install`
* Add another maven configuration to run the program with the following parameters:
`nbm:cluster nbm:run-platform`