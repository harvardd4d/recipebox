package main

import (
	"container/list"
	_ "database/sql"
	"fmt"
	"github.com/codegangsta/negroni"
	"github.com/gorilla/pat"
	"github.com/jmoiron/sqlx"
	_ "github.com/mattn/go-sqlite3"
	"net/http"
	"strconv"
)

// the main database
var recipedb *RecipeDatabase

func HomeHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "You've reached the recipebox hotline")
}

func RecipeHandler(w http.ResponseWriter, r *http.Request) {
	id, _ := strconv.Atoi(r.URL.Query().Get(":id"))
	recipe, err := recipedb.GetRecipe(id)

	if err != nil {
		fmt.Fprintf(w, err.Error())
		return
	}

	fmt.Fprintf(w, "You asked for recipe %v!", recipe.Name)
}

func RecipeJSONHandler(w http.ResponseWriter, r *http.Request) {
	id, _ := strconv.Atoi(r.URL.Query().Get(":id"))
	recipe, err := recipedb.GetRecipe(id)

	if err != nil {
		fmt.Fprintf(w, err.Error())
		return
	}

	fmt.Fprintf(w, "%v", recipe.ToJSON())
}

func RecipeAdvancedJSONHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	strict, err := strconv.Atoi(r.FormValue("strict"))
	name := r.FormValue("name")
	cuisine, _ := strconv.Atoi(r.FormValue("cuisine"))
	season, _ := strconv.Atoi(r.FormValue("season"))
	mealtype, _ := strconv.Atoi(r.FormValue("mealtype"))

	var recipes *list.List
	if strict == 0 {
		recipes, err = recipedb.GetRecipesLoose(name, cuisine, mealtype, season)
	} else {
		recipes, err = recipedb.GetRecipesStrict(name, cuisine, mealtype, season)
	}

	request := "" // TODO efficiency

	if err == nil {
		for e := recipes.Front(); e != nil; e = e.Next() {
			rec := e.Value.(*Recipe)
			request += rec.ToJSON() + "\n"
		}
	} else {
		request = err.Error()
	}
	fmt.Fprintf(w, request)
}

func main() {
	// Read database, check to see if we can open
	db_file := "testdb.sqlite"
	db, _ := sqlx.Open("sqlite3", db_file)
	err := db.Ping()
	if err != nil {
		panic(fmt.Sprintf("Unable to open database %v.", db_file))
	}
	recipedb = &RecipeDatabase{DB: db}

	// Setting up the router
	r := pat.New()
	r.Post("/recipes/jsonsearch", RecipeAdvancedJSONHandler)
	r.Get("/recipes/{id:[0-9]+}/json", RecipeJSONHandler)
	r.Get("/recipes/{id:[0-9]+}", RecipeHandler)
	r.Get("/", HomeHandler)

	// Setting up middleware (server, logging layer)
	n := negroni.Classic()
	n.UseHandler(r)
	n.Run(":8080")
}
