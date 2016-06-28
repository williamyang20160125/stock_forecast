function orientationChange(){
  if(window.orientation==180||window.orientation==0){  //竖屏 
    orientationAction.orientation = 'portrait';
  }   
  if(window.orientation==90||window.orientation==-90){   //横屏
	orientationAction.orientation = 'landscape';
  } 
  
  if(orientationAction.orientation === 'portrait'){//竖屏 
	  //machineGroup
	  if(orientationAction.action==='machineGroup-home'){//初始
		 $("#col-left").css({
 			 width:'100%',
 			 display:'block'
 		 });
 		 
 		 $("#col-right").css({
 			 display:'none'
 		 });
 		 
 		 $("#col-right .panel .panel-heading .panel-title .fui-arrow-left").css({
 			 display:'block'
 		 }); 
 		 
 		 $("#editGroupDiv").css({
 			display:'none'
 		 });
 		
 		 $("#editMachineDiv").css({
 			display:'none'
 		 });
 		 
 		 $("#addMachineToGroupDiv").css({
 			display:'none'
 		 });
	  }else if (orientationAction.action==='machineGroup-showMachine'){//点击服务器组
		 $("#col-left").css({
 			 display:'none'
 		 });
	  
		 $("#col-right").css({
			 width:'100%',
 			 display:'block'
 		 });
 		 
 		 $("#col-right .panel .panel-heading .panel-title .fui-arrow-left").css({
 			 display:'block'
 		 }); 
 		 
 		 $("#editGroupDiv").css({
 			display:'none'
 		 });
 		 
 		 $("#editMachineDiv").css({
 			display:'none'
 		 });
 		 
 	     $("#addMachineToGroupDiv").css({
 			display:'none'
 		 });
	  }else if(orientationAction.action==='machineGroup-editGroup'){//编辑服务器组
		 $("#col-left").css({
 			 display:'none'
 		 });
	  
		 $("#col-right").css({
 			 display:'none'
 		 });
 		 
 		 $("#col-right .panel .panel-heading .panel-title .fui-arrow-left").css({
 			 display:'none'
 		 }); 
 		 
 		 $("#editGroupDiv").css({
 			display:'block'
 		 });
 		 
 		 $("#editMachineDiv").css({
 			display:'none'
 		 });
 		 
 		 $("#addMachineToGroupDiv").css({
 			display:'none'
 		 });
 		 
	  }else if(orientationAction.action==='machineGroup-editMachine'){//编辑服务器
		 $("#col-left").css({
 			 display:'none'
 		 });
	  
		 $("#col-right").css({
 			 display:'none'
 		 });
 		 
 		 $("#col-right .panel .panel-heading .panel-title .fui-arrow-left").css({
 			 display:'none'
 		 }); 
 		 
 		 $("#editGroupDiv").css({
 			display:'none'
 		 });
 		 
 		 $("#editMachineDiv").css({
 			display:'block'
 		 });
 		 
 		 $("#addMachineToGroupDiv").css({
 			display:'none'
 		 });
	  }else if(orientationAction.action==='machineGroup-addMachineToGroup'){//添加服务器到组
		 $("#col-left").css({
 			 display:'none'
 		 });
	  
		 $("#col-right").css({
 			 display:'none'
 		 });
 		 
 		 $("#col-right .panel .panel-heading .panel-title .fui-arrow-left").css({
 			 display:'none'
 		 }); 
 		 
 		 $("#editGroupDiv").css({
 			display:'none'
 		 });
 		 
 		 $("#editMachineDiv").css({
 			display:'none'
 		 });
 		 
 		 $("#addMachineToGroupDiv").css({
 			display:'block'
 		 });
	  }
	  //contact
	  else if(orientationAction.action==='contact-home'){//初始
		  $("#contactHomeContainer").css({
			  display:'block'
		  });
		  $("#editContactDiv").css({
			  display:'none'
		  });
		  $("#contactRolesDiv").css({
			  display:'none'
		  });
		  $("#unContactRolesDiv").css({
			  display:'none'
		  })
	  }else if(orientationAction.action==='contact-editContact'){
		  $("#contactHomeContainer").css({
			  display:'none'
		  });
		  $("#editContactDiv").css({
			  display:'block'
		  });
		  $("#contactRolesDiv").css({
			  display:'none'
		  });
		  $("#unContactRolesDiv").css({
			  display:'none'
		  })
		  
	  }else if(orientationAction.action==='contact-showContactRoles'){
		  $("#contactHomeContainer").css({
			  display:'none'
		  });
		  $("#editContactDiv").css({
			  display:'none'
		  });
		  $("#contactRolesDiv").css({
			  display:'block'
		  });
		  $("#unContactRolesDiv").css({
			  display:'none'
		  });
		  
	  }else if(orientationAction.action==='contact-addContactRoles'){
		  $("#contactHomeContainer").css({
			  display:'none'
		  });
		  $("#editContactDiv").css({
			  display:'none'
		  });
		  $("#contactRolesDiv").css({
			  display:'none'
		  });
		  $("#unContactRolesDiv").css({
			  display:'block'
		  });
		  
	  }
	  //role
	  else if(orientationAction.action==='role-home'){
		  $("#roleHomeContainer").css({
			  display:'block'
		  });
		  $("#editRoleDiv").css({
			  display:'none'
		  });
		  $("#roleContactsDiv").css({
			  display:'none'
		  });
		  $("#unRoleContactsDiv").css({
			  display:'none'
		  });
	  } else if(orientationAction.action==='role-editRole'){
		  $("#roleHomeContainer").css({
			  display:'none'
		  });
		  $("#editRoleDiv").css({
			  display:'block'
		  });
		  $("#roleContactsDiv").css({
			  display:'none'
		  });
		  $("#unRoleContactsDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='role-showRoleContacts'){
		  $("#roleHomeContainer").css({
			  display:'none'
		  });
		  $("#editRoleDiv").css({
			  display:'none'
		  });
		  $("#roleContactsDiv").css({
			  display:'block'
		  });
		  $("#unRoleContactsDiv").css({
			  display:'none'
		  });
		  
	  }else if(orientationAction.action==='role-addRoleContacts'){
		  $("#roleHomeContainer").css({
			  display:'none'
		  });
		  $("#editRoleDiv").css({
			  display:'none'
		  });
		  $("#roleContactsDiv").css({
			  display:'none'
		  });
		  $("#unRoleContactsDiv").css({
			  display:'block'
		  });
		  
	  }
	  //group
	  else if(orientationAction.action==='group-home'){
		  $("#groupHomeContainer").css({
			  display:'block'
		  });
		  $("#editGroupDiv").css({
			  display:'none'
		  });
		  $("#groupMachinesDiv").css({
			  display:'none'
		  });
		  $("#unGroupMachinesDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='group-editGroup'){
		  $("#groupHomeContainer").css({
			  display:'none'
		  });
		  $("#editGroupDiv").css({
			  display:'block'
		  });
		  $("#groupMachinesDiv").css({
			  display:'none'
		  });
		  $("#unGroupMachinesDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='group-showGroupMachines'){
		  $("#groupHomeContainer").css({
			  display:'none'
		  });
		  $("#editGroupDiv").css({
			  display:'none'
		  });
		  $("#groupMachinesDiv").css({
			  display:'block'
		  });
		  $("#unGroupMachinesDiv").css({
			  display:'none'
		  });
		  
	  }else if(orientationAction.action==='group-addGroupMachines'){
		  $("#groupHomeContainer").css({
			  display:'none'
		  });
		  $("#editGroupDiv").css({
			  display:'none'
		  });
		  $("#groupMachinesDiv").css({
			  display:'none'
		  });
		  $("#unGroupMachinesDiv").css({
			  display:'block'
		  });
		  
	  }
	  //machine
	  else if(orientationAction.action==='machine-home'){
		  $("#machineHomeContainer").css({
			  display:'block'
		  });
		  $("#editMachineDiv").css({
			  display:'none'
		  });
		  $("#machineGroupsDiv").css({
			  display:'none'
		  });
		  $("#unMachineGroupsDiv").css({
			  display:'none'
		  });
		  $("#machineMonitorsDiv").css({
			  display:'none'
		  });
	  } else if(orientationAction.action==='machine-editMachine'){
		  $("#machineHomeContainer").css({
			  display:'none'
		  });
		  $("#editMachineDiv").css({
			  display:'block'
		  });
		  $("#machineGroupsDiv").css({
			  display:'none'
		  });
		  $("#unMachineGroupsDiv").css({
			  display:'none'
		  });
		  $("#machineMonitorsDiv").css({
			  display:'none'
		  });
	  } else if(orientationAction.action==='machine-showMachineGroups'){
		  $("#machineHomeContainer").css({
			  display:'none'
		  });
		  $("#editMachineDiv").css({
			  display:'none'
		  });
		  $("#machineGroupsDiv").css({
			  display:'block'
		  });
		  $("#unMachineGroupsDiv").css({
			  display:'none'
		  });
		  $("#machineMonitorsDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='machine-addMachineGroups'){
		  $("#machineHomeContainer").css({
			  display:'none'
		  });
		  $("#editMachineDiv").css({
			  display:'none'
		  });
		  $("#machineGroupsDiv").css({
			  display:'none'
		  });
		  $("#unMachineGroupsDiv").css({
			  display:'block'
		  });
		  $("#machineMonitorsDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='machine-showMachineMonitors'){
		  $("#machineHomeContainer").css({
			  display:'none'
		  });
		  $("#editMachineDiv").css({
			  display:'none'
		  });
		  $("#machineGroupsDiv").css({
			  display:'none'
		  });
		  $("#unMachineGroupsDiv").css({
			  display:'none'
		  });
		  $("#machineMonitorsDiv").css({
			  display:'block'
		  });
	  }
	  //monitor
	  else if(orientationAction.action==='monitor-home'){
		  $("#monitorHomeContainer").css({
			  display:'block'
		  });
		  $("#editMonitorDiv").css({
			  display:'none'
		  });
		  $("#monitorMachinesDiv").css({
			  display:'none'
		  });
		  $("#unMonitorMachinesDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='monitor-editMonitor'){
		  $("#monitorHomeContainer").css({
			  display:'none'
		  });
		  $("#editMonitorDiv").css({
			  display:'block'
		  });
		  $("#monitorMachinesDiv").css({
			  display:'none'
		  });
		  $("#unMonitorMachinesDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='monitor-showMonitorMachines'){
		  $("#monitorHomeContainer").css({
			  display:'none'
		  });
		  $("#editMonitorDiv").css({
			  display:'none'
		  });
		  $("#monitorMachinesDiv").css({
			  display:'block'
		  });
		  $("#unMonitorMachinesDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='monitor-addMonitorMachines'){
		  $("#monitorHomeContainer").css({
			  display:'none'
		  });
		  $("#editMonitorDiv").css({
			  display:'none'
		  });
		  $("#monitorMachinesDiv").css({
			  display:'none'
		  });
		  $("#unMonitorMachinesDiv").css({
			  display:'block'
		  });
	  }
	  
	  
	  
	  
  }else if(orientationAction.orientation === 'landscape'){//横屏
	  if(orientationAction.action==='machineGroup-home'){//初始
		  $("#col-left").css({
			 width:'50%',
			 display:'block'
		 });
		 
		 $("#col-right").css({
			 width:'50%',
			 display:'block'
		 });
		 
		 $("#col-right .panel .panel-heading .panel-title .fui-arrow-left").css({
			 display:'none'
		 });
		 
		 $("#editGroupDiv").css({
 			display:'none'
 		 });
		 
		 $("#editMachineDiv").css({
 			display:'none'
 		 });
		 
		 $("#addMachineToGroupDiv").css({
 			display:'none'
 		 });
	  }else if (orientationAction.action==='machineGroup-showMachine'){//点击服务器组
		 $("#col-left").css({
			 width:'50%',
 			 display:'block'
 		 });
	  
		 $("#col-right").css({
			 width:'50%',
 			 display:'block'
 		 });
 		 
 		 $("#col-right .panel .panel-heading .panel-title .fui-arrow-left").css({
 			 display:'none'
 		 }); 
 		 
 		 $("#editGroupDiv").css({
 			display:'none'
 		 });
 		 
 		 $("#editMachineDiv").css({
 			display:'none'
 		 });
 		 
 		 $("#addMachineToGroupDiv").css({
  			display:'none'
  		 });
	  }else if(orientationAction.action==='machineGroup-editGroup'){//编辑服务器组
		 $("#col-left").css({
 			 display:'none'
 		 });
	  
		 $("#col-right").css({
 			 display:'none'
 		 });
 		 
 		 $("#col-right .panel .panel-heading .panel-title .fui-arrow-left").css({
 			 display:'none'
 		 }); 
 		 
 		 $("#editGroupDiv").css({
 			display:'block'
 		 });
 		 
 		 $("#editMachineDiv").css({
 			display:'none'
 		 });
 		 
 		 $("#addMachineToGroupDiv").css({
  			display:'none'
  		 });
	  }else if(orientationAction.action==='machineGroup-editMachine'){//编辑服务器
		 $("#col-left").css({
 			 display:'none'
 		 });
	  
		 $("#col-right").css({
 			 display:'none'
 		 });
 		 
 		 $("#col-right .panel .panel-heading .panel-title .fui-arrow-left").css({
 			 display:'none'
 		 }); 
 		 
 		 $("#editGroupDiv").css({
 			display:'none'
 		 });
 		 
 		 $("#editMachineDiv").css({
 			display:'block'
 		 });
 		 
 		 $("#addMachineToGroupDiv").css({
  			display:'none'
  		 });
	  }else if(orientationAction.action==='machineGroup-addMachineToGroup'){//添加服务器到组
		 $("#col-left").css({
 			 display:'none'
 		 });
	  
		 $("#col-right").css({
 			 display:'none'
 		 });
 		 
 		 $("#col-right .panel .panel-heading .panel-title .fui-arrow-left").css({
 			 display:'none'
 		 }); 
 		 
 		 $("#editGroupDiv").css({
 			display:'none'
 		 });
 		 
 		 $("#editMachineDiv").css({
 			display:'none'
 		 });
 		 
 		 $("#addMachineToGroupDiv").css({
 			display:'block'
 		 });
	  }
	  //contact
	  else if(orientationAction.action==='contact-home'){//初始
		  $("#contactHomeContainer").css({
			  display:'block'
		  });
		  $("#editContactDiv").css({
			  display:'none'
		  });
		  $("#contactRolesDiv").css({
			  display:'none'
		  });
		  $("#unContactRolesDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='contact-editContact'){
		  $("#contactHomeContainer").css({
			  display:'none'
		  });
		  $("#editContactDiv").css({
			  display:'block'
		  });
		  $("#contactRolesDiv").css({
			  display:'none'
		  });
		  $("#unContactRolesDiv").css({
			  display:'none'
		  });
		  
	  }else if(orientationAction.action==='contact-showContactRoles'){
		  $("#contactHomeContainer").css({
			  display:'none'
		  });
		  $("#editContactDiv").css({
			  display:'none'
		  });
		  $("#contactRolesDiv").css({
			  display:'block'
		  });
		  $("#unContactRolesDiv").css({
			  display:'none'
		  });
		  
	  }else if(orientationAction.action==='contact-addContactRoles'){
		  $("#contactHomeContainer").css({
			  display:'none'
		  });
		  $("#editContactDiv").css({
			  display:'none'
		  });
		  $("#contactRolesDiv").css({
			  display:'none'
		  });
		  $("#unContactRolesDiv").css({
			  display:'block'
		  });
		  
	  }
	  //role
	  else if(orientationAction.action==='role-home'){
		  $("#roleHomeContainer").css({
			  display:'block'
		  });
		  $("#editRoleDiv").css({
			  display:'none'
		  });
		  $("#roleContactsDiv").css({
			  display:'none'
		  });
		  $("#unRoleContactsDiv").css({
			  display:'none'
		  });
	  } else if(orientationAction.action==='role-editRole'){
		  $("#roleHomeContainer").css({
			  display:'none'
		  });
		  $("#editRoleDiv").css({
			  display:'block'
		  });
		  $("#roleContactsDiv").css({
			  display:'none'
		  });
		  $("#unRoleContactsDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='role-showRoleContacts'){
		  $("#roleHomeContainer").css({
			  display:'none'
		  });
		  $("#editRoleDiv").css({
			  display:'none'
		  });
		  $("#roleContactsDiv").css({
			  display:'block'
		  });
		  $("#unRoleContactsDiv").css({
			  display:'none'
		  });
		  
	  }else if(orientationAction.action==='role-addRoleContacts'){
		  $("#roleHomeContainer").css({
			  display:'none'
		  });
		  $("#editRoleDiv").css({
			  display:'none'
		  });
		  $("#roleContactsDiv").css({
			  display:'none'
		  });
		  $("#unRoleContactsDiv").css({
			  display:'block'
		  });
		  
	  }
	  //group
	  else if(orientationAction.action==='group-home'){
		  $("#groupHomeContainer").css({
			  display:'block'
		  });
		  $("#editGroupDiv").css({
			  display:'none'
		  });
		  $("#groupMachinesDiv").css({
			  display:'none'
		  });
		  $("#unGroupMachinesDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='group-editGroup'){
		  $("#groupHomeContainer").css({
			  display:'none'
		  });
		  $("#editGroupDiv").css({
			  display:'block'
		  });
		  $("#groupMachinesDiv").css({
			  display:'none'
		  });
		  $("#unGroupMachinesDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='group-showGroupMachines'){
		  $("#groupHomeContainer").css({
			  display:'none'
		  });
		  $("#editGroupDiv").css({
			  display:'none'
		  });
		  $("#groupMachinesDiv").css({
			  display:'block'
		  });
		  $("#unGroupMachinesDiv").css({
			  display:'none'
		  });
		  
	  }else if(orientationAction.action==='group-addGroupMachines'){
		  $("#groupHomeContainer").css({
			  display:'none'
		  });
		  $("#editGroupDiv").css({
			  display:'none'
		  });
		  $("#groupMachinesDiv").css({
			  display:'none'
		  });
		  $("#unGroupMachinesDiv").css({
			  display:'block'
		  });
		  
	  }
	  //machine
	  else if(orientationAction.action==='machine-home'){
		  $("#machineHomeContainer").css({
			  display:'block'
		  });
		  $("#editMachineDiv").css({
			  display:'none'
		  });
		  $("#machineGroupsDiv").css({
			  display:'none'
		  });
		  $("#unMachineGroupsDiv").css({
			  display:'none'
		  });
		  $("#machineMonitorsDiv").css({
			  display:'none'
		  });
	  } else if(orientationAction.action==='machine-editMachine'){
		  $("#machineHomeContainer").css({
			  display:'none'
		  });
		  $("#editMachineDiv").css({
			  display:'block'
		  });
		  $("#machineGroupsDiv").css({
			  display:'none'
		  });
		  $("#unMachineGroupsDiv").css({
			  display:'none'
		  });
		  $("#machineMonitorsDiv").css({
			  display:'none'
		  });
	  } else if(orientationAction.action==='machine-showMachineGroups'){
		  $("#machineHomeContainer").css({
			  display:'none'
		  });
		  $("#editMachineDiv").css({
			  display:'none'
		  });
		  $("#machineGroupsDiv").css({
			  display:'block'
		  });
		  $("#unMachineGroupsDiv").css({
			  display:'none'
		  });
		  $("#machineMonitorsDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='machine-addMachineGroups'){
		  $("#machineHomeContainer").css({
			  display:'none'
		  });
		  $("#editMachineDiv").css({
			  display:'none'
		  });
		  $("#machineGroupsDiv").css({
			  display:'none'
		  });
		  $("#unMachineGroupsDiv").css({
			  display:'block'
		  });
		  $("#machineMonitorsDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='machine-showMachineMonitors'){
		  $("#machineHomeContainer").css({
			  display:'none'
		  });
		  $("#editMachineDiv").css({
			  display:'none'
		  });
		  $("#machineGroupsDiv").css({
			  display:'none'
		  });
		  $("#unMachineGroupsDiv").css({
			  display:'none'
		  });
		  $("#machineMonitorsDiv").css({
			  display:'block'
		  });
	  }
	  //monitor
	  else if(orientationAction.action==='monitor-home'){
		  $("#monitorHomeContainer").css({
			  display:'block'
		  });
		  $("#editMonitorDiv").css({
			  display:'none'
		  });
		  $("#monitorMachinesDiv").css({
			  display:'none'
		  });
		  $("#unMonitorMachinesDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='monitor-editMonitor'){
		  $("#monitorHomeContainer").css({
			  display:'none'
		  });
		  $("#editMonitorDiv").css({
			  display:'block'
		  });
		  $("#monitorMachinesDiv").css({
			  display:'none'
		  });
		  $("#unMonitorMachinesDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='monitor-showMonitorMachines'){
		  $("#monitorHomeContainer").css({
			  display:'none'
		  });
		  $("#editMonitorDiv").css({
			  display:'none'
		  });
		  $("#monitorMachinesDiv").css({
			  display:'block'
		  });
		  $("#unMonitorMachinesDiv").css({
			  display:'none'
		  });
	  }else if(orientationAction.action==='monitor-addMonitorMachines'){
		  $("#monitorHomeContainer").css({
			  display:'none'
		  });
		  $("#editMonitorDiv").css({
			  display:'none'
		  });
		  $("#monitorMachinesDiv").css({
			  display:'none'
		  });
		  $("#unMonitorMachinesDiv").css({
			  display:'block'
		  });
	  }
	  
	 
  }
}

function returnMachineGroupByFlush(){
	var groupId = $("#returnGroup").data("groupId");
	showGroupMachines(groupId);
	//returnMachine();
}

window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", orientationChange, true);   

function showContainer(action){
	orientationAction.action = action;
	orientationAction.onChange();
}


function returnGroup(){
	var action = 'machineGroup-home';
	showContainer(action);
}

function returnMachine(){
	var action = 'machineGroup-showMachine';
	showContainer(action);
}

function returnContactHome(){
	var action = 'contact-home';
	showContainer(action);
}




