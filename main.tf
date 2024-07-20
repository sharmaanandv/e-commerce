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

resource "google_compute_global_address" "lb_ip" {
  name = "lb-ip-address"
}

resource "google_compute_url_map" "default" {
  name            = "url-map"
  default_service = google_compute_backend_service.default.self_link
}

resource "google_compute_backend_service" "default" {
  name        = "backend-service"
  port_name   = "http"
  protocol    = "HTTP"
  timeout_sec = 10
  connection_draining {
    draining_timeout_sec = 30
  }
}

resource "google_compute_target_http_proxy" "default" {
  name    = "http-lb-proxy"
  url_map = google_compute_url_map.default.self_link
}

resource "google_compute_global_forwarding_rule" "default" {
  name       = "http-rule"
  ip_address = google_compute_global_address.lb_ip.address
  port_range = "80"
  target     = google_compute_target_http_proxy.default.self_link
}

resource "google_compute_backend_service" "api_gateway_backend" {
  count     = length(var.regions)
  name      = "api-gateway-backend-${element(var.regions, count.index)}"
  port_name = "http"
  protocol  = "HTTP"

  backend {
    group = google_compute_instance_group.api_gateway_instance_group[count.index].self_link
  }

  health_checks = [google_compute_health_check.default.self_link]
}

resource "google_compute_backend_service" "auth_service_backend" {
  count     = length(var.regions)
  name      = "auth-service-backend-${element(var.regions, count.index)}"
  port_name = "http"
  protocol  = "HTTP"

  backend {
    group = google_compute_instance_group.auth_service_instance_group[count.index].self_link
  }

  health_checks = [google_compute_health_check.default.self_link]
}

resource "google_compute_backend_service" "order_service_backend" {
  count     = length(var.regions)
  name      = "order-service-backend-${element(var.regions, count.index)}"
  port_name = "http"
  protocol  = "HTTP"

  backend {
    group = google_compute_instance_group.order_service_instance_group[count.index].self_link
  }

  health_checks = [google_compute_health_check.default.self_link]
}

resource "google_compute_backend_service" "product_service_backend" {
  count     = length(var.regions)
  name      = "product-service-backend-${element(var.regions, count.index)}"
  port_name = "http"
  protocol  = "HTTP"

  backend {
    group = google_compute_instance_group.product_service_instance_group[count.index].self_link
  }

  health_checks = [google_compute_health_check.default.self_link]
}

resource "google_compute_health_check" "default" {
  name                = "http-health-check"
  check_interval_sec  = 5
  timeout_sec         = 5
  healthy_threshold   = 2
  unhealthy_threshold = 2

  http_health_check {
    port = "80"
  }
}

resource "google_compute_instance_group" "api_gateway_instance_group" {
  count = length(var.regions)
  name  = "api-gateway-instance-group-${element(var.regions, count.index)}"

  instance = google_compute_instance.api_gateway[count.index].self_link
}

resource "google_compute_instance_group" "auth_service_instance_group" {
  count = length(var.regions)
  name  = "auth-service-instance-group-${element(var.regions, count.index)}"

  instance = google_compute_instance.auth_service[count.index].self_link
}

resource "google_compute_instance_group" "order_service_instance_group" {
  count = length(var.regions)
  name  = "order-service-instance-group-${element(var.regions, count.index)}"

  instance = google_compute_instance.order_service[count.index].self_link
}

resource "google_compute_instance_group" "product_service_instance_group" {
  count = length(var.regions)
  name  = "product-service-instance-group-${element(var.regions, count.index)}"

  instance = google_compute_instance.product_service[count.index].self_link
}

resource "google_compute_instance" "api_gateway" {
  count = length(var.regions)
  name  = "api-gateway-instance-${element(var.regions, count.index)}"

  machine_type = "e2-medium"

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-9"
    }
  }

  network_interface {
    network = "default"
    access_config {
    }
  }
}

resource "google_compute_instance" "auth_service" {
  count = length(var.regions)
  name  = "auth-service-instance-${element(var.regions, count.index)}"

  machine_type = "e2-medium"

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-9"
    }
  }

  network_interface {
    network = "default"
    access_config {
    }
  }
}

resource "google_compute_instance" "order_service" {
  count = length(var.regions)
  name  = "order-service-instance-${element(var.regions, count.index)}"

  machine_type = "e2-medium"

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-9"
    }
  }

  network_interface {
    network = "default"
    access_config {
    }
  }


}

resource "google_compute_instance" "product_service" {
  count = length(var.regions)
  name  = "product-service-instance-${element(var.regions, count.index)}"

  machine_type = "e2-medium"

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-9"
    }
  }

  network_interface {
    network = "default"
    access_config {
    }
  }


}
