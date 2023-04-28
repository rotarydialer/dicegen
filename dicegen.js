const yargs = require("yargs");

const generate = (wordCount, wordlist, phraseCount, outFile) => {
  // TODO: implement output to file
  if (phraseCount === 1) {
    console.log(`Generating a passphrase with ${wordCount} words...`);
  } else {
    console.log(
      `Generating ${phraseCount} passphrases, each with ${wordCount} words...`
    );
  }
  console.log();

  let codeWordMap = new Map();
  codeWordMap = loadWordlist(wordlist);

  const phrases = generateNPhrases(phraseCount, wordCount, codeWordMap);

  phrases.forEach((phrase) => {
    console.log(`     ${phrase}`);
  });

  console.log();
  console.log("Done.");
};

const generateNPhrases = (phraseCount, wordCount, codeWordMap) => {
  let phrases = [];

  if (phraseCount === 1) return [generatePhrase(wordCount, codeWordMap)];

  for (let c = 1; c <= phraseCount; c++) {
    phrases.push(generatePhrase(wordCount, codeWordMap));
  }

  return phrases;
};

const generatePhrase = (wordCount, codeWordMap) => {
  let codesRandomized = [];

  for (let i = 0; i < wordCount; i++) {
    codesRandomized.push(concatDieRolls());
  }

  let phrase = [];

  for (let j = 0; j < codesRandomized.length; j++) {
    phrase.push(codeWordMap.get(codesRandomized[j]));
  }

  return phrase.join(" ");
};

const loadWordlist = (filename) => {
  const fs = require("fs");
  const lines = fs
    .readFileSync(filename)
    .toString()
    .split("\n")
    .filter((line) => line.length > 0);

  let fileMap = new Map();
  lines.forEach((line) => {
    const linePair = line.split("\t");
    fileMap.set(linePair[0], linePair[1]);
  });

  return fileMap;
};

function getRandomInt(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

const concatDieRolls = (numSides = 6, numDigits = 5) => {
  let rolls = [];
  for (let i = 0; i < numDigits; i++) {
    rolls.push(getRandomInt(1, numSides));
  }

  return rolls.join("");
};

const args = yargs
  .command(
    "*",
    "Generate a passphrase, based on Diceware. (https://en.wikipedia.org/wiki/Diceware)",
    (yargs) => {}
  )
  .option("length", {
    alias: "l",
    describe: "number of words to include in the phrase",
    default: 6,
  })
  .option("phrases", {
    alias: "p",
    describe: "number of phrases to generate",
    default: 1,
  })
  .option("corpus", {
    alias: "c",
    describe: "wordlist (input) filename",
    default: "resources/wordlist.txt",
  })
  .option("output", {
    alias: "o",
    describe: "output filename",
  })
  .help().argv;

const wordCount = args.length;
const phraseCount = args.phrases;
const wordlist = args.corpus;
const outFile = args.output;

generate(wordCount, wordlist, phraseCount, outFile);
