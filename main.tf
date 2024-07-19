provider "google" {
  project = "my-gcp-project-id"
  region  = "us-central1"
}

variable "regions" {
  default = ["us-central1", "us-east1"]
}

resource "google_cloud_run_service" "api_gateway" {
  count    = length(var.regions)
  name     = "api-gateway-${element(var.regions, count.index)}"
  location = element(var.regions, count.index)

  template {
    spec {
      containers {
        image = "gcr.io/my-gcp-project-id/api-gateway:latest"
      }
      scaling {
        min_instance_count = 5
        max_instance_count = 30
      }
    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }
}

resource "google_cloud_run_service" "auth_service" {
  count    = length(var.regions)
  name     = "auth-service-${element(var.regions, count.index)}"
  location = element(var.regions, count.index)

  template {
    spec {
      containers {
        image = "gcr.io/my-gcp-project-id/auth-service:latest"
      }
      scaling {
        min_instance_count = 5
        max_instance_count = 30
      }
    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }
}

resource "google_cloud_run_service" "order_service" {
  count    = length(var.regions)
  name     = "order-service-${element(var.regions, count.index)}"
  location = element(var.regions, count.index)

  template {
    spec {
      containers {
        image = "gcr.io/my-gcp-project-id/order-service:latest"
      }
      scaling {
        min_instance_count = 5
        max_instance_count = 30
      }
    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }
}

resource "google_cloud_run_service" "product_service" {
  count    = length(var.regions)
  name     = "product-service-${element(var.regions, count.index)}"
  location = element(var.regions, count.index)

  template {
    spec {
      containers {
        image = "gcr.io/my-gcp-project-id/product-service:latest"
      }
      scaling {
        min_instance_count = 5
        max_instance_count = 30
      }
    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }
}

# Allow unauthenticated access to Cloud Run services
resource "google_project_iam_binding" "run_invoker" {
  project = "my-gcp-project-id"

  role = "roles/run.invoker"

  members = [
    "allUsers",
  ]
}
