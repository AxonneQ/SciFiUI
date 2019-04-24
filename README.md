# Object-Oriented Programming Assignment - SciFi UI

Name: **Igor Bolek**

Student Number: **C17487376**

# Description of the assignment
We were asked to create a Sci-Fi interface with a variety of animations and effects that could be theorically used in a movie. Demonstration of Polymorphism, Abstraction, Inheritance and Interfaces was also required as well as exploration of Processing framework. I decided to explore 3D renderer adding additional layers of complexity and challenges that were not as easy to overcome compared to 2D renderer.

My idea was to create a spaceship dashboard that would display all nearby planets on semi-transparent screen where a planet can be selected for further inspection. There is also a second screen showing information of currently selected planet, and a hologram displaying that planet with its moons and orbit. I wanted to experiment with lighting, rotation and creation of custom shapes. Ultimately, this allowed me to create an effect impossible to achieve in 2D renderer.

# Instructions
## Video demonstrating functionality:
[![YouTube](https://img.youtube.com/vi/VB5xHbBoAfs/0.jpg)](https://youtu.be/VB5xHbBoAfs)

All interaction is done in the controls console on the bottom of the screen.
1. System must first be turned on.
2. Scan must be performed before planets can be shown
3. Hologram projection can be turned on/off after scan.
4. Once Hologram projection is running, user can cycle through planets which display information about them on the right screen.
5. Zooming is possible with either mouseWheel or clicking the Zoom button.


# How it works

# What I am most proud of in the assignment

# Markdown Tutorial

This is *emphasis*

This is a bulleted list

- Item
- Item

This is a numbered list

1. Item
1. Item

This is a [hyperlink](http://bryanduggan.org)

# Headings
## Headings
#### Headings
##### Headings

This is code:

```Java
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

So is this without specifying the language:

```
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

This is an image using a relative URL:

![An image](images/p8.png)

This is an image using an absolute URL:

![A different image](https://bryanduggandotorg.files.wordpress.com/2019/02/infinite-forms-00045.png?w=595&h=&zoom=2)

This is a youtube video:

[![YouTube](http://img.youtube.com/vi/J2kHSSFA4NU/0.jpg)](https://www.youtube.com/watch?v=J2kHSSFA4NU)

This is a table:

| Heading 1 | Heading 2 |
|-----------|-----------|
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |

