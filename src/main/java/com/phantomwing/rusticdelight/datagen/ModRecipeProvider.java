package com.phantomwing.rusticdelight.datagen;

import com.phantomwing.rusticdelight.RusticDelight;
import com.phantomwing.rusticdelight.item.ModItems;
import com.phantomwing.rusticdelight.tags.CommonTags;
import com.phantomwing.rusticdelight.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;
import vectorwing.farmersdelight.data.recipe.CookingRecipes;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        buildCraftingRecipes(output);
        buildCuttingRecipes(output);
        buildCookingRecipes(output);
    }

    private void buildCraftingRecipes(@NotNull RecipeOutput output) {
        // Calamari
        foodCookingRecipes(output, ModItems.CALAMARI.get(), ModItems.COOKED_CALAMARI.get(), 0.35f);
        foodCookingRecipes(output, ModItems.CALAMARI_SLICE.get(), ModItems.COOKED_CALAMARI_SLICE.get(), 0.35f);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CALAMARI_ROLL.get(), 2)
                .requires(ModItems.CALAMARI_SLICE.get())
                .requires(ModItems.CALAMARI_SLICE.get())
                .requires(vectorwing.farmersdelight.common.registry.ModItems.COOKED_RICE.get())
                .unlockedBy(getHasName(ModItems.CALAMARI_ROLL.get()), has(ModItems.CALAMARI_ROLL.get()))
                .save(output);

        // Potato
        foodCookingRecipes(output, ModItems.POTATO_SLICES.get(), ModItems.BAKED_POTATO_SLICES.get(), 0.35f);

        // Cotton
        oneToOne(output, RecipeCategory.MISC, ModItems.COTTON_BOLL.get(), Items.STRING, 1);
        horizontalRecipe(output, RecipeCategory.MISC, ModItems.COTTON_BOLL.get(), Items.PAPER, 3);
        twoBytwo(output, RecipeCategory.MISC, ModItems.COTTON_BOLL.get(), vectorwing.farmersdelight.common.registry.ModItems.CANVAS.get(), 1);
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.COTTON_SEEDS.get(), ModItems.COTTON_SEEDS_BAG.get());
        storageItemRecipes(output, RecipeCategory.MISC, ModItems.COTTON_BOLL.get(), ModItems.COTTON_BOLL_CRATE.get());
    }

    private void buildCuttingRecipes(@NotNull RecipeOutput output) {
        // Cotton
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.WILD_COTTON.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.COTTON_SEEDS.get(), 1)
                .addResultWithChance(ModItems.COTTON_BOLL.get(), 0.3F)
                .addResultWithChance(Items.WHITE_DYE, 0.1F)
                .build(output, ModItems.WILD_COTTON.getId());

        // Food
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.POTATO), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.POTATO_SLICES.get(), 2)
                .build(output, ModItems.POTATO_SLICES.getId());
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.CALAMARI.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.CALAMARI_SLICE.get(), 2)
                .addResult(Items.BONE_MEAL)
                .build(output, ModItems.CALAMARI_SLICE.getId());
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.COOKED_CALAMARI.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.COOKED_CALAMARI_SLICE.get(), 2)
                .addResult(Items.BONE_MEAL)
                .build(output, ModItems.COOKED_CALAMARI_SLICE.getId());

        // Salvaging
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ItemTags.WOOL), Ingredient.of(Tags.Items.TOOLS_SHEAR), Items.STRING, 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(RusticDelight.MOD_ID, "wool"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ItemTags.WOOL_CARPETS), Ingredient.of(Tags.Items.TOOLS_SHEAR), Items.STRING, 1)
                .build(output, ResourceLocation.fromNamespaceAndPath(RusticDelight.MOD_ID, "wool_carpet"));
    }

    private void buildCookingRecipes(@NotNull RecipeOutput output) {
        // Cooking oil
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.COOKING_OIL.get(), 2, CookingRecipes.FAST_COOKING, CookingRecipes.SMALL_EXP, Items.GLASS_BOTTLE)
                .addIngredient(ModTags.Items.COOKING_OIL_INGREDIENTS)
                .addIngredient(ModTags.Items.COOKING_OIL_INGREDIENTS)
                .addIngredient(ModTags.Items.COOKING_OIL_INGREDIENTS)
                .addIngredient(ModTags.Items.COOKING_OIL_INGREDIENTS)
                .addIngredient(ModTags.Items.COOKING_OIL_INGREDIENTS)
                .addIngredient(ModTags.Items.COOKING_OIL_INGREDIENTS)
                .unlockedByAnyIngredient(ModItems.COTTON_SEEDS)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .save(output, ModItems.COOKING_OIL.getId());

        // Batter
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.BATTER.get(), 2, CookingRecipes.FAST_COOKING, CookingRecipes.SMALL_EXP, Items.BOWL)
                .addIngredient(CommonTags.FOODS_MILK)
                .addIngredient(Tags.Items.EGGS)
                .addIngredient(Items.WHEAT)
                .addIngredient(Items.WHEAT)
                .unlockedByAnyIngredient(Items.MILK_BUCKET, vectorwing.farmersdelight.common.registry.ModItems.MILK_BOTTLE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .save(output, ModItems.BATTER.getId());

        // Spring Rolls
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.SPRING_ROLLS.get(), 2, CookingRecipes.NORMAL_COOKING, CookingRecipes.MEDIUM_EXP)
                .addIngredient(ModItems.COOKING_OIL)
                .addIngredient(CommonTags.FOODS_DOUGH)
                .addIngredient(CommonTags.FOODS_LEAFY_GREEN)
                .addIngredient(vectorwing.farmersdelight.common.tag.ModTags.CABBAGE_ROLL_INGREDIENTS)
                .unlockedByAnyIngredient(ModItems.COOKING_OIL)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output, ModItems.SPRING_ROLLS.getId());

        // Fruit Beignet
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.FRUIT_BEIGNET.get(), 1, CookingRecipes.NORMAL_COOKING, CookingRecipes.MEDIUM_EXP)
                .addIngredient(ModItems.COOKING_OIL)
                .addIngredient(CommonTags.FOODS_DOUGH)
                .addIngredient(Tags.Items.FOODS_FRUIT)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(ModItems.COOKING_OIL)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output, ModItems.FRUIT_BEIGNET.getId());

        // Fried Chicken
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.FRIED_CHICKEN.get(), 1, CookingRecipes.NORMAL_COOKING, CookingRecipes.MEDIUM_EXP, Items.BOWL)
                .addIngredient(ModItems.COOKING_OIL)
                .addIngredient(ModItems.BATTER)
                .addIngredient(CommonTags.FOODS_RAW_CHICKEN)
                .addIngredient(CommonTags.FOODS_ONION)
                .unlockedByAnyIngredient(ModItems.COOKING_OIL)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output, ModItems.FRIED_CHICKEN.getId());

        // Fried Calamari
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.FRIED_CALAMARI.get(), 1, CookingRecipes.NORMAL_COOKING, CookingRecipes.MEDIUM_EXP, Items.BOWL)
                .addIngredient(ModItems.COOKING_OIL)
                .addIngredient(ModItems.BATTER)
                .addIngredient(CommonTags.FOODS_RAW_CALAMARI)
                .addIngredient(CommonTags.FOODS_TOMATO)
                .unlockedByAnyIngredient(ModItems.COOKING_OIL)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output, ModItems.FRIED_CALAMARI.getId());

    }

    protected static void oneToOne(RecipeOutput recipeOutput, RecipeCategory category, ItemLike item, ItemLike result, int count) {
        ShapelessRecipeBuilder.shapeless(category, result, count)
                .requires(item)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, result));
    }

    protected static void horizontalRecipe(RecipeOutput recipeOutput, RecipeCategory category, ItemLike item, ItemLike result, int count) {
        ShapedRecipeBuilder.shaped(category, result, count)
                .pattern("###")
                .define('#', item)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, result));
    }

    protected static void twoBytwo(RecipeOutput recipeOutput, RecipeCategory category, ItemLike item, ItemLike result, int count) {
        ShapedRecipeBuilder.shaped(category, result, count)
                .pattern("##")
                .pattern("##")
                .define('#', item)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, result));
    }

    protected static void storageItemRecipes(RecipeOutput recipeOutput, RecipeCategory category, ItemLike item, ItemLike storageItem) {
        // From item to storageItem
        ShapedRecipeBuilder.shaped(category, storageItem)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', item)
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput, getRecipeName(item, storageItem));

        // From storageItem to item
        ShapelessRecipeBuilder.shapeless(category, item, 9)
                .requires(storageItem)
                .unlockedBy(getHasName(storageItem), has(storageItem))
                .save(recipeOutput, getRecipeName(storageItem, item));
    }

    protected static void foodCookingRecipes(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience) {
        foodSmelting(recipeOutput, material, result, experience, 200);
        foodSmoking(recipeOutput, material, result, experience, 100); // Smoking is twice as fast
        foodCampfireCooking(recipeOutput, material, result, experience, 600); // Campfire cooking takes three times longer
    }

    protected static void foodSmelting(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), RecipeCategory.FOOD, result, experience, cookingTime, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void foodSmoking(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), RecipeCategory.FOOD, result, experience, cookingTime, RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, RusticDelight.MOD_ID + ":" + getItemName(result) + "_from_smoking");
    }

    protected static void foodCampfireCooking(@NotNull RecipeOutput recipeOutput, @NotNull ItemLike material, @NotNull ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(material), RecipeCategory.FOOD, result, experience, cookingTime, RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, RusticDelight.MOD_ID + ":" + getItemName(result) + "_from_campfire_cooking");
    }

    protected static String getRecipeName(ItemLike item, ItemLike result) {
        return RusticDelight.MOD_ID + ":" + getConversionRecipeName(result, item);
    }
}
