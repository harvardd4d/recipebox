package main

import (
	"container/list"
	"encoding/json"
	"fmt"
	"github.com/jmoiron/sqlx"
)

type Recipe struct {
	Id             int    `json:"id"`
	Name           string `json:"name"`
	Description    string `json:"description"`
	Cuisine        int    `json:"cuisine"`
	Mealtype       int    `json:"mealtype"`
	Season         int    `json:"season"`
	Ingredientlist string `json:"ingredientlist"`
	Instructions   string `json:"instructions"`
}

func (self *Recipe) ToJSON() string {
	result, _ := json.Marshal(self) // TODO: ERROR
	return string(result)
}

type RecipeDatabase struct {
	DB *sqlx.DB
}

func (this *RecipeDatabase) GetRecipe(id int) (recipe *Recipe, err error) {
	row := this.DB.QueryRowx("SELECT * FROM recipes WHERE id=?", id)
	recipe = new(Recipe)
	err = row.StructScan(recipe)
	return
}

func (this *RecipeDatabase) GetRecipesStrict(name string, cuisine, mealtype, season int) (recipes *list.List, err error) {
	fmt.Printf("Getting %v %v %v %v", name, cuisine, mealtype, season)
	rows, err := this.DB.Queryx("SELECT * "+
		"FROM recipes WHERE name LIKE ? AND "+
		"cuisine=? AND "+
		"mealtype=? AND "+
		"season=?",
		name, cuisine, mealtype, season)

	if err != nil {
		panic("1. " + err.Error())
	}

	recipes = list.New()

	for rows.Next() {
		recipe := new(Recipe)
		err = rows.StructScan(recipe)
		if err == nil {
			recipes.PushBack(recipe)
		} else {
			panic("2. " + err.Error()) // TODO
		}
	}
	return
}

func (this *RecipeDatabase) GetRecipesLoose(name string, cuisine, mealtype, season int) (recipes *list.List, err error) {
	recipes, err = this.GetRecipesStrict("%"+name+"%", cuisine, mealtype, season)
	return
}
