from rest_framework import serializers
from .models import Project, Technology

class TechnologySerializer(serializers.ModelSerializer):
  class Meta:
    model = Technology
    fields = ['name']

class ProjectSerializer(serializers.ModelSerializer):
  technologies = TechnologySerializer(many=True)
  class Meta:
    model = Project
    fields = (
      'name',
      'image_url',
      'summary',
      'description',
      'project_type',
      'technologies',
      'project_date',
      'slug'
    )

class ProjectSummarySerializer(serializers.ModelSerializer):
  technologies = TechnologySerializer(many=True)
  class Meta:
    model = Project
    fields = ('name', 'image_url', 'summary', 'project_type', 'technologies', 'project_date', 'slug')
