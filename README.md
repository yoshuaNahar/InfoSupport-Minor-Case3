# Kantilever Webwinkel

# Leesmij (na de minor)
TODO:
- Sla alles op in 1 GitLab project
- Zip in deze git project, daarin zit alle documentatie. Gebruik de buildstraat docs als referentie voor de toekomst


## Day-to-Day Development
1. Open a unix based terminal (Git Bash, Docker Quickstart Terminal, etc.).
2. Navigate to the root of the project.
3. Execute the following command `source setenv.sh`
4. To actually lift your docker environment, execute the following command `docker-compose up`

Please note that this doesn't include our own microservices and front-end. Those can be run manually through your IDE.

## Front end Development
Install the following (Required):
- Node `https://nodejs.org/en/download/`
  - For Non-Windows users: `sudo apt-get install node` <- Depends on which distro which version it will install
     - you can also install `nvm` for node, EZ for windows and linux.
- Bower `npm install -g bower`

1. Open a unix based terminal.
2. Install Polymer cli : `npm install -g polymer-cli`
3. Navigate to /front-end-webwinkel
4. Run `bower install` to get all dependencies
5. Run `polymer serve` to run the app

For more polymer-cli commands, check the polymer-cli github

Happy coding :)
