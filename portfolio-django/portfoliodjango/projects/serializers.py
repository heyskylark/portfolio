from rest_framework import serializers
from .models import Project

class ProjectSerializer(serializers.ModelSerializer):
  class Meta:
    model = Project
    fields = (
      'id',
      'image_url',
      'name',
      'summary',
      'technologies',
      'description',
      'project_type',
      'slug',
      'project_date'
    )