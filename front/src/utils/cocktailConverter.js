const splitAndTrim = (data, delimiter) => {
  return data.split(delimiter).map((tag) => tag.trim());
};

const dataToCocktailRequest = (data) => {
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

export default dataToCocktailRequest;
