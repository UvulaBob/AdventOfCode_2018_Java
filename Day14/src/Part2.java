import java.util.ArrayList;

public class Part2 {

    public static void main(String[] args) {
        String target = "919901";
        int firstElfPosition = 0;
        int secondElfPosition = 1;
        ArrayList<Integer> recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);

        StringBuilder recipeString = new StringBuilder("37");
        int deletedRecipes = 0;

        while (true) {
            // Calculate New Recipe
            int newRecipe = recipes.get(firstElfPosition) + recipes.get(secondElfPosition);

            // Check for match and append New Recipe Digits To List
            for (String digit : String.valueOf(newRecipe).split("")) {
                int digitToAdd = Integer.parseInt(String.valueOf(digit));
                recipes.add(digitToAdd);
                recipeString.append(digitToAdd);
            }

            if (recipeString.toString().contains(target)) {
                break;
            }

            if (recipeString.length() > target.length()) {
                int indexToDeleteUpTo = (recipeString.length() - target.length());
                recipeString.delete(0, indexToDeleteUpTo + 1);
                deletedRecipes += indexToDeleteUpTo + 1;
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

        System.out.println(String.format("Sequence %s appeared after %s recipes.", target, deletedRecipes));
        System.out.println("Done!");
    }
}
