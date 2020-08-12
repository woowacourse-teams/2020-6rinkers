const splitAndTrim = (data, delimiter) => {
  return data.split(delimiter).map((tag) => tag.trim());
};

export const convertDataToCocktailRequest = (data) => {
  const cocktailRequest = {
    name: data.name,
    abv: parseFloat(data.abv),
    description: data.description,
    origin: data.origin,
    imageUrl: data.imageUrl,
    tag: splitAndTrim(data.tag, ","),
    sweet: parseInt(data.sweet),
    sour: parseInt(data.sour),
    bitter: parseInt(data.bitter),
    liquor: splitAndTrim(data.liquor, ","),
    liquorQuantity: splitAndTrim(data.liquorQuantity, ","),
    special: splitAndTrim(data.special, ","),
    specialQuantity: splitAndTrim(data.specialQuantity, ","),
  };
  return cocktailRequest;
};

export const convertCocktailToInputData = (cocktail) => {
  const inputData = {
    id: cocktail.id,
    name: cocktail.name,
    abv: parseFloat(cocktail.abv),
    description: cocktail.description,
    origin: cocktail.origin,
    imageUrl: cocktail.imageUrl,
    tag: tagsToInputTags(cocktail.tags),
    sweet: cocktail.sweet ? 1 : 0,
    sour: cocktail.sour ? 1 : 0,
    bitter: cocktail.bitter ? 1 : 0,
    liquor: cocktail.recipe.map(e => e.ingredient).join(","),
    liquorQuantity: cocktail.recipe.map(e => e.quantity).join(","),
    special: "",
    specialQuantity: "",
  };
  return inputData;
};

const tagsToInputTags = (tags) => {
  return tags.map(e => e.name).join(",");
}
