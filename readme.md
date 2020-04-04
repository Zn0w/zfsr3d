# Zn0w File System Representation 3D
I am making a program that can visualise file system (your dirs and files).
Inspired by the file system visualisation program featured in the dinosaur movie.
## Log
### 3/19/2020
I have just added a basic visual representation of the test directory (and all it's contents, recursively).
![Progress as of 3/19/2020](log/03_19_2020.png)
### 3/21/2020
* dynamic camera: camera movement, camera zoom (scaling)
* better positioning of graphic nodes
* keyboard & mouse input handling
* graphic representation: lines connecting parent-child, show all names toggle
![Progress as of 3/21/2020 gif](log/03_21_2020_1.gif)
![Progress as of 3/21/2020 1](log/03_21_2020_1.png)
![Progress as of 3/21/2020 1](log/03_21_2020_2.png)
### 4/4/2020
* treeview: hide/expand child nodes feature
![Progress as of 4/4/2020 1](log/04_04_2020_1.gif)
* added a dynamic 2D view of file system (user can navigate through file system using this mode, like the standard system's file explorer)
* switching between view modes (treeview, dynamic 2d view)
![Progress as of 4/4/2020 2](log/04_04_2020_2.gif)
* started to work on 3D rendering pipeline
* added RenderObject3D data structures, test rendering for the dynamic 3D view
![Progress as of 4/4/2020 3](log/04_04_2020_3.png)