#!/bin/bash

cd ~/dev/projects/admin

sbt "testOnly org.genericConfig.admin.models.user.AddAlreadyExistingUserSpecs \
              org.genericConfig.admin.models.user.AddNewUserSpecs \
              org.genericConfig.admin.models.user.DeleteExistingUserSpecs \
              org.genericConfig.admin.models.user.UpdateUserNameSpecs"