terraform {
  required_version = ">= 0.13"

  required_providers {
    huaweicloud = {
      source = "local-registry/huaweicloud/huaweicloud"
      version = ">= 1.20.0"
    }
  }
}

provider "huaweicloud" {
  region     = "cn-north-1"
  access_key = "NGASOM7EFQJNV6Y6RG8R"
  secret_key = "spr4yDSO4ezhKjCCHGn3pVhyRq1IB5b4E7MJnNtx"
}

// data "huaweicloud_availability_zones" "myaz" {}

// output "hw_aaz"{
//     value = data.huaweicloud_availability_zones.myaz
// }

#data "huaweicloud_compute_flavors" "${hwInstanceName}" {
#  availability_zone = "cn-north-1a"
#  performance_type  = "normal"
#  cpu_core_count    = 1
#  memory_size       = 2
#}

// data "huaweicloud_images_image" "hw_image_id" {
//   name        = "Ubuntu 18.04 server 64bit"
//   most_recent = true
// }

// output "hw_instance_type" {
//     value = data.huaweicloud_compute_flavors.ins_1c2u.ids[0]
// }

#data "huaweicloud_images_image" "myimage" {
#  name = "Ubuntu 18.04 server 64bit"
#  most_recent = true
#}
#
#output "my_image_output" {
#  value = data.huaweicloud_images_image.myimage.id
#}

resource "huaweicloud_vpc" "${hwVpcName}" {

  name = "${hwVpcName}"
  cidr = "192.168.0.0/16"
}

resource "huaweicloud_vpc_subnet" "subnet1" {
  name = "subnet-web"
  cidr = "192.168.10.0/24"
  gateway_ip = "192.168.10.1"
  vpc_id = huaweicloud_vpc.${hwVpcName}.id
  dns_list = ["100.125.1.250", "100.125.129.250"]
}


resource "huaweicloud_compute_instance" "${hwInstanceName}" {
  name = "${hwInstanceName}"
  admin_pass = "${rootPassword}"
  image_id = "${amiOutId}"
  flavor_id = "${instanceType}"
  availability_zone = "cn-north-1a"
  security_groups = ["Sys-default"]
  network {
    uuid = huaweicloud_vpc_subnet.subnet1.id
  }
}

resource "huaweicloud_vpc_eip" "${hwEipName}" {
  publicip {
    type = "5_bgp"
  }
  bandwidth {
    name = "mybandwidth"
    size = 8
    share_type = "PER"
    charge_mode = "traffic"
  }
}
resource "huaweicloud_compute_eip_associate" "${hwEipAssociationName}" {
  public_ip = huaweicloud_vpc_eip.${hwEipName}.address
  instance_id = huaweicloud_compute_instance.${hwInstanceName}.id
}

resource "huaweicloud_evs_volume" "${diskName}" {
  name = "/dev/${diskName}"
  availability_zone = "cn-north-1a"
  volume_type = "SAS"
  size = ${diskSize}
}
resource "huaweicloud_compute_volume_attach" "${hwDiskAttachName}" {
  instance_id = huaweicloud_compute_instance.${hwInstanceName}.id
  volume_id = huaweicloud_evs_volume.${diskName}.id
}

output "public_ip" {
  value = huaweicloud_compute_eip_associate.${hwEipAssociationName}.public_ip
}


// data "huaweicloud_vpc_subnet" "mynet" {
//   name = "subnet-default"
// }