[![Modrinth Version](https://img.shields.io/modrinth/v/1YGXPQtK?style=flat&logo=modrinth&logoColor=%2300AF5C&label=Get%20it%20on%20modrinth&color=%2300AF5C)](https://modrinth.com/project/1YGXPQtK)

## A simple tool to generate big sign texts
#### Developed for [BTE Germany](https://bte-germany.de/de) \([GitHub](https://github.com/BTE-Germany)\). <br>

<img width="1502" height="101" alt="2025-09-02_21 42 56_edited" src="https://github.com/user-attachments/assets/78fe5f74-a868-42b2-89bb-e9354aa22baa" />
<br>
Font "signletters" by Hugleton

# Usage:
You need to be in creative mode to use the command. <br>
``/givesign <material> <color> <font> <text>`` <br>
You may need to adjust some characters by hand, depending on the combination

# Adding your own font:
## Fonts only for your server
1. Create a JSON file with the format described below
2. Place it in ``/plugins/SignTextGenerator`` or any subfolder of it
3. Restart your server and use the font name (without .json) in the command

## Make your fonts available for everyone
1. Fork this repository
2. Add your font as FONT_NAME.json file to ``/src/main/resources/fonts/``
3. Create a pull request to get your font added to the plugin

# Font file format
To add your font, the JSON file needs to have the following: <br>
a) A number of letters that fit on one regular sign <br>
b) A number of letters that fit on one hanging sign <br>
c) Weather your letters have enough space on either side or the generator should put additional spaces between letters <br>
d) A representation for every character in form of the four lines a sign has <br>
<br>
### Example:
```JSON
{
  "maxCharsPerSign" : 3,
  "maxCharsPerHangingSign" : 2,
  "ownLetterSeparation": false,
  "charMap" : {
    " ": ["      ","      ","      ","      "],
    "A": ["◢▊█▊◣","█  ݀█","███","█  ݀█"],
    "B": ["██▊◣","█▄▊◤","█▀▊◣","██▊◤"],
    "C": ["◢▊█▊◣","█▏  ▀","█▏  ▄","◥▊█▊◤"],
	"D": ["██▊◣","█  ▏█","█  ▏█","██▊◤"]
   }
}
```
