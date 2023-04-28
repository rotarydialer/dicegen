# dicegen

A simple passphrase generator, based on [Diceware](https://en.wikipedia.org/wiki/Diceware).

## Usage

Generate a single passphrase, with the default length of 6 words:

```
$ node dicegen

Generating a passphrase with 6 words...

     lease bundy bird oc freon music
```

Change the length of the passphrase with the `--length` | `-l` flag:

```
$ node dicegen --length 3

Generating a passphrase with 3 words...

     germ sung muffin
```

### Other options

Generate multiple passphrases with the `--phrases` | `-p` flag:

```
$ node dicegen --phrases 3
Generating 3 passphrases, each with 6 words...

     qualm xm saxon gadget dig peel rapid
     crump chime seal erato 85th glued thyme
     chile their rail brady eat wring thine
```
