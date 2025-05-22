{{/*
Expand the name of the chart.
*/}}
{{- define "bytepeak.name" -}}
{{- default .Chart.Name .Values.nameOverride | lower | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "bytepeak.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | lower | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride | lower -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | lower | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | lower | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{/*
Create chart's version and application version as part of the labels
*/}}
{{- define "bytepeak.version" -}}
{{- printf "%s-%s" .Chart.Version .Chart.AppVersion -}}
{{- end -}}

{{/*
Create the label for the selector
*/}}
{{- define "bytepeak.selectorLabels" -}}
app.kubernetes.io/name: {{ include "bytepeak.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end -}}

{{/*
Create the labels
*/}}
{{- define "bytepeak.labels" -}}
{{ include "bytepeak.selectorLabels" . }}
helm.sh/chart: {{ include "bytepeak.chart" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end -}}

{{/*
Create the name of the service account to use
*/}}
{{- define "bytepeak.serviceAccountName" -}}
{{- if .Values.serviceAccount.create -}}
{{- default (include "bytepeak.fullname" .) .Values.serviceAccount.name -}}
{{- else -}}
{{- default "default" .Values.serviceAccount.name -}}
{{- end -}}
{{- end -}}

{{/*
Common labels
*/}}
{{- define "bytepeak.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}