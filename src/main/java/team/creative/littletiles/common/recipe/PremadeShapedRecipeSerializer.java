package team.creative.littletiles.common.recipe;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import team.creative.littletiles.common.item.ItemPremadeStructure;

public class PremadeShapedRecipeSerializer implements RecipeSerializer<ShapedRecipe> {
    
    @Override
    public ShapedRecipe fromJson(ResourceLocation recipeLoc, JsonObject recipeJson) {
        ShapedRecipe recipe = RecipeSerializer.SHAPED_RECIPE.fromJson(recipeLoc, recipeJson);
        
        return new ShapedRecipe(recipe.getId(), recipe.getGroup(), recipe.category(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), ItemPremadeStructure.of(
            GsonHelper.getAsString(GsonHelper.getAsJsonObject(recipeJson, "result"), "structure")), recipe.showNotification());
    }
    
    @Override
    public ShapedRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
        return RecipeSerializer.SHAPED_RECIPE.fromNetwork(location, buffer);
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, ShapedRecipe recipe) {
        RecipeSerializer.SHAPED_RECIPE.toNetwork(buffer, recipe);
    }
    
}
