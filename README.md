# dehiscent-rpg

An open world-building experiment and rpg.

### Contributing ###

As the game is still in its baby gro we're not accepting any major additions to mechanics for a while until the balance issues have settled down and the world has been fleshed out a bit more. However feel free to suggest any feature requests on the issues page so that they can be discussed by fellow contributers and considered for future 

There are a few places where we really need contributions right now, these are:
  * Creating new cells and building the world!
  * Bug fixes and addressing balance issues
  * Enhancements to current mechanics which improve player experience

Certain features are currently in progress and should be finished soon, so don't bother working on them yet- however any suggestions are welcome!
  * Saving and loading
  * Updates to the class structure of items to allow better composition

1. Fork repo on GitHub
2. Clone your fork to create a working copy
  ```
  git clone https://github.com/Username/dehiscent-rpg.git
  ```
3. Navigate to the root directory of your new working copy. Create and switch to a feature branch to work in.

  ```
  cd ~/my_projects/dehiscent-rpg
  git checkout -b feature_branch</pre>
  ```
4. As you make changes in order to implement the feature you're working on, add the new or altered files to the staging area. Once a group of related changes are completed, bundle them up into a commit. Use git push to push your commits to your remote repository.
  ```
  git add NewClass.java 
  git add altered_file.txt 
  git commit -m "My appropriately long, detailed and informative comment" 
  git push origin feature_branch</pre>
  ```
5. When finished with the feature, switch back into the master branch and merge the feature branch into it
  ```
  git checkout master
  git merge feature_branch</pre>
  ```
6. Push your changes to your master branch
  ```
  git push origin master</pre>
  ```
7. Stay up to date and pull in other peoples' changes which have made it into upstream, this will help avoid future conflicts
  ```
  git fetch upstream
  git checkout master
  git merge upstream master</pre>
  ```
8. When you're happy with the contributions you've added to your fork, submit a pull request on GitHub and wait for your additions to be accepted into upstream!
                                                                                            </div>

### Using Issues ###

Please use the issues as much as possible, particularly for the following!

  * :beetle: Bugs
  * :last_quarter_moon: Balance issues
  * :cookie: Feature requests
