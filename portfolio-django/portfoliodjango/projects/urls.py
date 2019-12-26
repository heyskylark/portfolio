from django.urls import path, include
from .views import ProjectView
from rest_framework import routers

urlpatterns = [
    path('projects/', ProjectView.list, name="ProjectView.list"),
    path('projects/<slug>/', ProjectView.retrieve, name='ProjectView.retrieve'),
]
