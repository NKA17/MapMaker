# MapMaker
Was not intended to be a collaborative project. If you are going to collaborate, allow me to apologize in advance.

# Forking
Make a fork and push all your junk to your own fork. When you make a PR (Pull Request), make it from your fork into this repository.

# Branching
* **Master** Keep the up to date with latest release branch. This is the branch which installs of the project *can* be made off of.
* **release/x** Release branches off of master when a new release epic is opened. These branches are the branches that installs *should* be made off of. Allowing the user to choose what version of the application they want. Every release branch should be fully functional, stable, and complete.
* **develop** Develop is the working branch. This is the most up to date and functional code, potentially unstable. Develop branch holds the current release epic features and bug fixes. When all the features charted for the release are complete, develop merges into release/x and then release/x into master.
* **feature/RPG-x-Title** Feature branches are for one feature only. Minimize scope of the feature and avoid creeping. When creating a new feature, check-out develop, pull latest, create your feature branch off of develop, do your work, commit, and then make a PR back into develop.
* **hotfix/release/x** When something becomes deprecated or a critical bug is found in a release, a hotfix branch is made for that release. The hotfixed release is only merged back into master if it is the current release.
You may have noticed that a release is branched off of master at the chronological start of the release epic and nothing is pushed to it until the release epic is finished. This means that the current release branch will be identical to its predecessor until the release is actually released (ie develop merged into current release)

# Git Flow
![alt text](https://github.com/NKA17/MapMaker/blob/develop/src/main/resources/github/gitflow.JPG?raw=true)

# Hot Fixes
If a hotfix is ever needed, the hotfix branch can pull latest from the current release, the fix can be made, and merged back into release. Then that release is merged back into master. Since different things can be deprecated in different ways between releases, a hotfix may need to be made for each release (hotfix/release/x).

# Stories/Feature
No story board currently exists, so for now just make feature branches for an intened change.

When a story board exists...
Pick a story (you can make one too). Create your feature branch from the develop branch. If your story is called 'RPG-19 Cool Feature' name your feature branch 'feature/RPG-19-CoolFeature'. After completing your work, make a PR from your feature into develop.
