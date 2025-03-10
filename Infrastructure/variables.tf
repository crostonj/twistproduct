# Define variables
variable "resource_group_name" {
  description = "The name of the resource group"
  type        = string
  default     = "croston-rg1"
}

variable "location" {
  description = "The location of the resources"
  type        = string
  default     = "Central US"
}

variable "storage_account_name" {
  description = "The name of the storage account"
  type        = string
  default     = "techtwistdatastorage"
}

variable "table_name" {
  description = "The name of the table storage"
  type        = string
  default     = "Products"
}
# End of variables.tf
