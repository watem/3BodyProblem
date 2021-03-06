# 3BodyProblem
Matthew Williams and Yulia Kosharych
# 2 approaches
This project has 2 main classes that drive the code: Main.java and NBody.java as well as a myriad of logic and data classes. The two main classes approach the problem in two seperate ways that are better suited for different tasks. 
# Main.java
Main follows an approach that overwrites the previous data points, allowing for longer scale running (tests involving over 300 Earth years) at higher accuracy, while also providing a real time graph of the positions. 
# NBody.java
The NBody class keeps all of the position points that it calculates, meaning that there is a finite limit to the accuracy and maximum time. On the other hand, the NBody method is more modular allowing for easy addition of more than three bodies. The NBody program also shows the entire orbital path that is calculated allowing for easier comparison between slightly different situations, and it can also switch to a rotating reference frame without losing accuracy to the lack of inertial effects. This can be done to any body of the user's choosing. The inertial reference frame is useful for observing phenomena that are unique to N body problems that have more than 2 bodies such as Lagrange points as well as "horseshoe" and "tadpole" orbits.
# Future exploration
There are also two files that provide the base for further expansion into the domain of electrostatic 3 body problems that have been included as a proof of concept but have not been fully fleshed out as they are not within the scope of the problem.
