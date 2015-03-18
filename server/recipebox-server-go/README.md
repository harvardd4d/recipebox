### recipebox-server-go

The RecipeBox web server, written in Go

### Setup

If you're new to Go, set up your GOPATH and Go workspace with
the instructions described in https://golang.org/doc/code.html.  Go
is rather picky about its workspace options but allows for some
very useful modularity as a result of its workspace organization.

`github.com/codegangsta/negroni`
`github.com/gorilla/pat`
`github.com/jmoiron/sqlx`
`github.com/mattn/go-sqlite3`

The above dependencies are needed.  Run 

    $ go get <package>

to install them.  We are currently trying to find a better way to resolve
dependencies.

### Building

Run `go build` in the recipebox-go-server folder to compile the code.
An executable named "recipebox-go-server" will be created.
Run the executable.  Currently it will attach itself to port 8080.

### Current implemented behavior

The following routes are currently implemented.

1. `GET /recipes/:id` displays the contents of the recipe with specified id.
2. `GET /recipes/:id/json` displays a json string of the recipe with specified id.
3. `POST /recipes/jsonsearch ? strict=[0,1] name=<string> season=<int> mealtype=<int> cuisine=<int>`
returns many json strings, seperated by newlines, of all the recipes that
satisfy the search criteria.