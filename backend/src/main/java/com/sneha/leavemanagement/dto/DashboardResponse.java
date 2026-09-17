package com.sneha.leavemanagement.dto;
public record DashboardResponse(long totalEmployees,long pendingLeaves,long approvedLeaves,long rejectedLeaves,
 int myBalance,long myPending,long myApproved,long teamEmployees,long teamPending) {}
