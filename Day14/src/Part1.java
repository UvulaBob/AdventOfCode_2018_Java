import java.util.ArrayList;

public class Part1 {

    public static void main(String[] args) {
        int maxNumberOfRecipes = 919901;
        int numberOfRecipesAfterMax = 10;
        int firstElfPosition = 0;
        int secondElfPosition = 1;
        ArrayList<Integer> recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);

        while (recipes.size() < maxNumberOfRecipes + numberOfRecipesAfterMax) {
            // Calculate New Recipe
            int newRecipe = recipes.get(firstElfPosition) + recipes.get(secondElfPosition);

            // Append New Recipe Digits To List
            for (String digit : String.valueOf(newRecipe).split("")) {
                recipes.add(Integer.parseInt(digit));
            }

            // Move Elves
            int firstElfRecipeValue = recipes.get(firstElfPosition);
            firstElfPosition += firstElfRecipeValue + 1;
            while (firstElfPosition > recipes.size() - 1) {
                firstElfPosition -= recipes.size();
            }

            int secondElfRecipeValue = recipes.get(secondElfPosition);
            secondElfPosition += secondElfRecipeValue + 1;
            while (secondElfPosition > recipes.size() - 1) {
                secondElfPosition -= recipes.size();
            }
        }

        StringBuilder lastRecipes = new StringBuilder();

        for (int i = maxNumberOfRecipes; i < maxNumberOfRecipes + numberOfRecipesAfterMax; i++){
            lastRecipes.append(recipes.get(i));
        }

        System.out.println(String.format("Last %s recipes: %s", numberOfRecipesAfterMax, lastRecipes));
        System.out.println("Done!");
    }
}
