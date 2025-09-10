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
1. Fork this repository
2. Add your font as JSON file to the resources/fonts directory
3. Feel free to create a PR, so others can also use your font

# Font file format
To add your font, the JSON file needs to have the following: <br>
a) A number of letters that fit on one regular sign (this number is halved and rounded up for hanging signs) <br>
b) A representation for every character in form of the four lines a sign has <br>
<br>
### Example:
```JSON
{
  "maxCharsPerSign" : 3,
  "charMap" : {
	" ": ["      ","      ","      ","      "],
	"A": ["◢▊█▊◣","█  ݀█","███","█  ݀█"],
	"B": ["██▊◣","█▄▊◤","█▀▊◣","██▊◤"],
	"C": ["◢▊█▊◣","█▏  ▀","█▏  ▄","◥▊█▊◤"],
	"D": ["██▊◣","█  ▏█","█  ▏█","██▊◤"]
   }
}
```
