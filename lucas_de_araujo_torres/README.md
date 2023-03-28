# Tech Test: Software Development Intern

# index

* [index](#index)
* [description](#description)
* [funcionalities](#funcionalities)
* [contributors](#contributors)


# Description
This project is a Test job provide by Axur. Its main purpose is, given a url of a website, to return the deepest text of the html code. If it has 2 or more texts in the same level, that will return the first occurrence.<br>
The html's code its divided in lines, where each can be an open tag(Ex: div, h1, etc), a close tag (Ex: /div, /h2, etc), or a text. The codes also doesn't have non-closing tags, like the br tag.<br>
We need to inform a message: “malformed HTML”, if the HTML is not made correctly, or a message: “URL connection error“, if it has some connection failure.<br>
This project was made with Java.

# Funcionalities
- To solve this problem, first i used a socket conection to get the HTML code from the URL link that is passed in the args. It's working in the <b>getHtmlCodeBySocket</b> method.<br>
- Then, i process the string of this code in an array of elements, where contains the tags and the texts, splited. The method what makes that is <b>htmlCodeTextProcess</b><br>
- After that, with this array, we can get the deeper element of a HTML code. We will use a data structure called Stack. I decided to implement this Stack by myself to show some of my skills in data structure, but we can also use the Stack Class of Java.<br>
- The <b>getDeeperText</b> method will work like this: It will create a stack and two arrays: one saves the level of the texts of the HTML code, and the another saves the text's in String. We stack up the elements in the stack. If the stack receives a colse HTML tag, we unstack the elements again until we find the same tag, but opening. In this process, we need to save the text and it's level depth in the two arrays created.<br>
- Finally, with the two arrays ready, we can get the first most deeper element in the method <b>getFirstMostDeeperText</b>. This returns the result of the problem.

# Contributors
This project was made for <b>Lucas Torres</b>, a student of Computer Science at the Universidade Federal do Ceará.
