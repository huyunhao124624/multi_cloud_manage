// data "alicloud_regions" "current_region_ds" {
//   current = true
// }

// output "current_region_id" {
//   value = "${data.alicloud_regions.current_region_ds.regions.0.id}"
// }

terraform {
  required_version = ">= 0.13"

  required_providers {
    alicloud = {
      source = "local-registry/alicloud/alicloud"
      version = "1.163.0"
    }
  }
}

provider "alicloud" {
  access_key = "LTAI5t7wPx2v7RkCxCHW5gm1"
  secret_key = "rCpkWp6rqcqhUCN8b9D5uSUUJpLunH"

}

# Create a new ECS instance for a VPC
resource "alicloud_security_group" "multi_cloud_manage_ali_security_group" {
  name        = "multi_cloud_manage_ali_security_group"
  description = "用于hyh访问阿里云"
  security_group_type = "normal"
  vpc_id      = alicloud_vpc.${aliVpcName}.id
}

resource "alicloud_security_group_rule" "allow_all_tcp" {
  type              = "ingress"
  ip_protocol       = "tcp"
  nic_type          = "intranet"
  policy            = "accept"
  port_range        = "1/65535"
  priority          = 1
  security_group_id = alicloud_security_group.multi_cloud_manage_ali_security_group.id
  cidr_ip           = "0.0.0.0/0"
}

data "alicloud_zones" "default" {
  available_disk_category     = "cloud_efficiency"
  available_resource_creation = "VSwitch"
}

# Create a new ECS instance for VPC
resource "alicloud_vpc" "${aliVpcName}" {
  cidr_block = "172.16.0.0/16"
}

resource "alicloud_vswitch" "${aliVswitchName}" {
  vpc_id            = alicloud_vpc.${aliVpcName}.id
  cidr_block        = "172.16.0.0/24"
  zone_id           = data.alicloud_zones.default.zones[0].id
  vswitch_name      = "${aliVswitchName}"
}

resource "alicloud_instance" ${aliInstanceName} {
  # cn-beijing
  availability_zone = data.alicloud_zones.default.zones[0].id
  security_groups   = alicloud_security_group.multi_cloud_manage_ali_security_group.*.id

  # series III
  instance_type              = "${instanceType}"
  system_disk_category       = "cloud_efficiency"
  system_disk_name           = "multi_cloud_manage_ali_system_disk_name"
  system_disk_description    = "system disk"
  image_id                   = "${amiOutId}"
  instance_name              = "${aliInstanceName}"
  vswitch_id                 = alicloud_vswitch.${aliVswitchName}.id
  internet_max_bandwidth_out = 10
  password                   = "${rootPassword}"
  data_disks {
    name        = "${diskName}"
    size        = ${diskSize}
    category    = "cloud_efficiency"
    description = "disk2"
  }
}

output "public_ip" {
  value = alicloud_instance.${aliInstanceName}.public_ip
}