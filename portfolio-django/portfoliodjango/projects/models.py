from django.db import models

class Project(models.Model):
  image_url = models.TextField()
  name = models.TextField()
  summary = models.TextField()
  technologies = models.ManyToManyField('Technology', through='ProjectTechnologies')
  description = models.TextField()
  project_type = models.TextField()
  slug = models.SlugField()
  project_date = models.DateTimeField()
  created_date = models.DateTimeField(auto_now_add=True)
  updated_date = models.DateTimeField(auto_now=True)

  def __str__(self):
    return self.name

  class Meta():
    db_table = 'projects'

class Technology(models.Model):
  name = models.TextField()

  class Meta():
    db_table = 'technologies'

class ProjectTechnologies(models.Model):
  project = models.ForeignKey(Project, on_delete=models.CASCADE)
  technology = models.ForeignKey(Technology, on_delete=models.CASCADE)

  class Meta():
    db_table = 'projects_technologies'
