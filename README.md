# Neocis Software Challenge Documentation
## Position: Internship - Software Engineering
### Tyrone Gallardo


# Overview
The software assessment was divided into two parts. For the sake of simplicity, each part was made into its own individual project. 
The challenge was coded using Java16 in its entirety. This repository contains the second part of the project. Here, you can find the 
source code, as well as a .bat and a runnable .jar file for easy execution. The rest of the project documentation, including an algorithm 
explanation and a system synopsis, can also be found in the repository.

The second part of the project requires that the user be able to select an arbitrary amount of points in the grid; the same 20x20 grid will 
be used for this part as well. After selecting any amount of points, the user will be able to click a **Generate** button, which will generate 
a circle that best fits the selected points. To determine the best-fit circle, a geometric least squares-based algorithm was required.

After generating a circle, the user can continue to generate additional circles by either adding or removing points and reclicking the 
**Generate** button. Marked points will display in blue, while un-marked buttons will display in gray.
