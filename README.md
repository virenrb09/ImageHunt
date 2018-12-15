# ImageHunt - KOTLIN

- Simple Flickr based image search app without using any third party libraries.

![alt text](https://raw.githubusercontent.com/virenrb09/ImageHunt/master/screenshots/Screenshot_1544871831.png)
![alt text](https://raw.githubusercontent.com/virenrb09/ImageHunt/master/screenshots/Screenshot_1544871844.png)
![alt text](https://raw.githubusercontent.com/virenrb09/ImageHunt/master/screenshots/Screenshot_1544871871.png)

Required Future Improvement :

# Image Caching 

- Currently the caching is done only in LRU in memory cache which can be extended to Memory snd Disk Cache for caching the images and reducing the data bandwidth used to load.

# SOLID principles

- SOC is implemented in current code but there are few places where improvement can be done.

# Dagger and RxAndroid

- Can create a MainComponent with @scope of MainActivity to provide all the dependencies for Activity.

# Generic Adapter for Pagination

- Generic Adapter for paginating the items and adding proper loading state

# Better UI :D

