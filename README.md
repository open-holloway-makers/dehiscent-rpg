# dehiscent-rpg

An open dark fantasy rpg and world building experiment.

### Running ###

Check out the Makefile for some useful configurations, but to just compile and run use:

  ```
  make run
  ```

### Contributing ###

##### What's Needed Now? #####

As the game is still in its baby gro we're not accepting any major additions to mechanics for a while until the balance issues have settled down and the world has been fleshed out a bit more. However feel free to suggest any feature requests on the issues page so that they can be discussed by fellow contributers and considered for future 

The way mechanics are constructed now might seem a little complex and unclear but check out the existing cells surrounding (0, 0). They're a bit rushed and lacking in content, but hopefully will give you a rought idea of how to create a cell, add items, enemies and merchants and resolve combat and transactions and some other tricks. 

There are a few places where we really need contributions right now, these are:
  * Creating new cells and building the world!
  * Bug fixes and addressing balance issues (there are many!)
  * Enhancements to current mechanics which improve player experience
  * Test suites and unit tests (there are none!)
  * A better (easier and more portable) way of running the game

Certain features are currently in progress and should be finished soon, so don't bother working on them yet- however any suggestions are welcome!
  * Saving and loading
  * Updates to the class structure of items to allow better composition

##### Adding a new cell ######

Cells make up the various areas and rooms in Dehiscent and together generate a mosaic-like world which can be explored by the player. Think of them like the individual screens in the old handheld Zelda games, that's the general size they should stick to. 

To add a new cell, create a class which implements Blank Cell and then override the abstract methods. Then in the main class Dehiscent.java, add your cell and its coordinates to the createMap() function. Any coordinates which conflict with those of other contributers will simple be moved appropriately after a discussion with the author.

The world is currently 32 x 32 cells, but can easily grow once it's filled up.

##### Workflow #####

All contributions should generally follow the following version control workflow. This project was originally created to test the workflow to be used on other open source projects, so please offer any feedback you have or problems you encounter!

1. Fork the repo 'open-holloway-makers/dehiscent-rpg' on GitHub
2. Clone your fork to create a working copy

  ```
  git clone https://github.com/Username/dehiscent-rpg.git
  ```

3. Navigate to the root directory of your new working copy. Create and switch to a feature branch to work in.

  ```
  cd ~/my_projects/dehiscent-rpg
  git checkout -b feature_branch
  ```

4. As you make changes in order to implement the feature you're working on, add the new or altered files to the staging area. Once a group of related changes are completed, bundle them up into a commit. Use git push to push your commits to your remote repository.

  ```
  git add NewClass.java 
  git add altered_file.txt 
  git commit -m "My appropriately long, detailed and informative comment" 
  git push origin feature_branch
  ```

5. When finished with the feature, switch back into the master branch and merge the feature branch into it

  ```
  git checkout master
  git merge feature_branch
  ```

6. Push your changes to your master branch

  ```
  git push origin master
  ```

7. Stay up to date and pull in other peoples' changes which have made it into upstream, this will help avoid future conflicts

  ```
  git fetch upstream
  git checkout master
  git merge upstream master
  ```

8. When you're happy with the contributions you've added to your fork, submit a pull request on GitHub and wait for your additions to be accepted into upstream!
                                                                                            
### Using Issues ###

Please use the issues as much as possible, particularly for the following!

  * :beetle: Bugs
  * :last_quarter_moon: Balance issues
  * :cookie: Feature requests
