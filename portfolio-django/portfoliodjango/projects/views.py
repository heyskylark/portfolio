from django.shortcuts import render, get_object_or_404
from django.http.response import JsonResponse
from rest_framework.views import APIView
from .models import Project
from .serializers import ProjectSerializer, ProjectSummarySerializer

class ProjectView(APIView):
  def list(self):
    queryset = Project.objects.all()
    serializer = ProjectSummarySerializer(queryset, many=True)
    return JsonResponse(serializer.data, safe=False)

  def retrieve(self, slug=None):
    queryset = Project.objects.all()
    project = get_object_or_404(queryset, slug=slug)
    serializer = ProjectSerializer(project)
    return JsonResponse(serializer.data)
