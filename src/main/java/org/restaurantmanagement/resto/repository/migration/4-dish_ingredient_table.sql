create TABLE IF NOT EXISTS DishIngredient(
    dish_id INT REFERENCES Dish(dish_id) ON delete CASCADE,
    ingredient_id INT REFERENCES Ingredient(ingredient_id) ON delete CASCADE,
    quantity FLOAT NOT NULL,
    PRIMARY KEY (dish_id, ingredient_id)
);