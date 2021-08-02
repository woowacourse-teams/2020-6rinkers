export const intro = [
  "안녕하세요! 반가워요.\n맛있는 칵테일을 추천해드릴게요.",
  "오늘도 한잔하시는군요!\n맛있는 칵테일을 추천해드릴게요.",
  "한잔의 여유를 즐겨보아요.\n맛있는 칵테일을 추천해드릴게요.",
  "오늘 같은 날에는 칵테일이 제격이죠!\n맛있는 칵테일을 추천해드릴게요.",
  "칵테일 한잔으로 기분이 좋아져요!",
];

export const bartenderSaying = [
  "좋아하는 칵테일 있으신가요?",
  "칵테일 한잔을 마시면 기분이 좋아지죠!",
  "음주는 즐겁고 건강하게!",
  "칵테일로 하루를 마무리하는 건 너무 즐거운 일이에요.",
  "칵테일과 얽힌 추억이 있으신가요?",
  "칵테일은 맛있어서 과음하실 수 있으니 조심하세요!",
  "바에 가서 저 같은 바텐더에게 칵테일에 관해 물어보시면 친절하게 대답해드린답니다~",
  "조심하세요🚨 칵테일의 매력에서 빠져나올 수 없으실테니까요😀",
];

export const recommendationQuestions = [
  intro[Math.floor(Math.random() * intro.length)], // INTRO
  "첫 질문입니다!\n이런 느낌의 칵테일은 어떠세요?", // CONCEPT
  "도수는 어느 정도가 좋으신가요?\n최저 도수와 최대 도수의 범위를 정해주세요!", // ABV
  "다음 중 좋아하는 재료가 있으신가요?", // INGREDIENT
  "마시고 싶은 칵테일의 맛을 골라주세요.", // TASTE
  "이제 한 가지만 더 여쭤볼게요.\n다음 중 먹고 싶지 않은 재료들을\n모두 골라주세요!", // DISLIKE
];

export const answerList = [
  {
    yes: "매우 좋아요",
    soso: "좋아요",
    no: "그저 그래요",
  },
];

export const userCocktailQuestions = {
  name: "칵테일의 이름을 정해주세요.",
  ingredients: "어떤 재료가 들어가요?",
  "quantity-unit": "어떤 잔으로 계량할까요?",
  quantity: "얼마나 넣으면 좋을까요?",
  recipe: "입력하신 정보를 확인해보세요.",
};

export const quantityUnits = [
  {
    id: 1,
    name: "소주잔",
    path: "/image/userCocktail/soju.png",
    quantityUnit: "SOJU",
    ml: 70,
  },
  {
    id: 2,
    name: "맥주잔",
    path: "/image/userCocktail/beer.png",
    quantityUnit: "BEER",
    ml: 200,
  },
  {
    id: 3,
    name: "양주잔",
    path: "/image/userCocktail/shot.png",
    quantityUnit: "SHOT",
    ml: 45,
  },
  {
    id: 4,
    name: "종이컵",
    path: "/image/userCocktail/paper.png",
    quantityUnit: "PAPER",
    ml: 180,
  },
  {
    id: 5,
    name: "스푼",
    path: "/image/userCocktail/spoon.png",
    quantityUnit: "SPOON",
    ml: 0,
  },
  {
    id: 6,
    name: "n개",
    path: "/image/userCocktail/piece.png",
    quantityUnit: "PIECE",
    ml: 0,
  },
];
