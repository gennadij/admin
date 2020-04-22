#!/bin/bash

cd ~/dev/projects/admin

sbt "testOnly org.genericConfig.admin.models.config.AddConfigSpecs\
              org.genericConfig.admin.models.config.AddConfigWithSameConfigUrlsSpecs \
              org.genericConfig.admin.models.config.AddSeveralConfigsSpecs \
              org.genericConfig.admin.models.config.DeleteConfigSpecs \
              org.genericConfig.admin.models.config.DeleteConfigWithDefectIdSpecs \
              org.genericConfig.admin.models.config.GetEmptyConfigsSpecs \
              org.genericConfig.admin.models.config.GetSeveralConfigsSpecs \
              org.genericConfig.admin.models.config.UpdateConfigSpecs"