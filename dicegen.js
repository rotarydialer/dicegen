const yargs = require("yargs");

const generatePhrase = (wordCount, wordlist, outFile) => {
  console.log(`Generating a ${wordCount} word passphrase...`);
  console.log();

  let codeWordMap = new Map();
  codeWordMap = loadWordlist(wordlist);

  console.log(codeWordMap);
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

const args = yargs
  .command(
    "*",
    "Generate a passphrase, based on Diceware. (https://en.wikipedia.org/wiki/Diceware)",
    (yargs) => {}
  )
  .option("length", {
    describe: "number of words to include in the phrase",
    default: 6,
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
const wordlist = args.corpus;
const outFile = args.output;

generatePhrase(wordCount, wordlist, outFile);
