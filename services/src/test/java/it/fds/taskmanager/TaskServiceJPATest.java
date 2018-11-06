package it.fds.taskmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.print.DocFlavor.STRING;
import javax.validation.constraints.AssertTrue;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.javafx.tk.Toolkit.Task;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import it.fds.taskmanager.dto.TaskDTO;




/**
 * Basic test suite to test the service layer, it uses an in-memory H2 database. 
 * 
 * TODO Add more and meaningful tests! :)
 *
 * @author <a href="mailto:damiano@searchink.com">Damiano Giampaoli</a>
 * @since 10 Jan. 2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TaskServiceJPATest extends Assert{

    @Autowired
    TaskService taskService;
   
  String taskTitle="";
  int flag=0;
  
  String preChangeUUID ="";
  String preChangeTaskTitle ="";  
  String preChangeStatus = "";
  long preChangePostponedAt=0;
	
  String postChangeUUID = "";
  String postChangeTaskTitle ="";
  String postChangeStatus = "";
  long postChangePostponedAt=0;
  Calendar postChangePostponedAtDate=null;
    
 /*   
    
    @Test
    public void testShowList1() {
        TaskDTO t = new TaskDTO();
        t.setTitle("Test task1");
        t.setStatus(TaskState.NEW.toString().toUpperCase());
        TaskDTO t1 = taskService.saveTask(t);
        TaskDTO tOut = taskService.findOne(t1.getUuid());
        assertEquals("Test task1", tOut.getTitle());
        List<TaskDTO> list = taskService.showList();
        System.out.println("ListSize in read and Write:"+list.size());
        assertEquals(2, list.size());
    }

 */   
   //Trying to insert a task with status New and fetching it with showlist
    @Test
    public void testShowList_with_1NewTask() {
    	
	    TaskDTO t1 = new TaskDTO();
	    flag=0;
	    taskTitle="Test Task 1 in TestSHowList";
    	t1.setTitle(taskTitle);
    	t1.setStatus(TaskState.NEW.toString().toUpperCase());
    	TaskDTO t2 = taskService.saveTask(t1);
    	
    	String uuid1 = t2.getUuid().toString();
    	List<TaskDTO> l1= taskService.showList();
    	System.out.println("uuid1:"+uuid1);
    	System.out.println("status should be postponed:"+t2.getStatus());
    	for(TaskDTO t: l1)
    	{
    	String uuid2=t.getUuid().toString();
    	System.out.println("uuid2:"+uuid2);
    		if(uuid1.equalsIgnoreCase(uuid2) && taskTitle.equalsIgnoreCase(t.getTitle()))//checking if the fetched task matches that with the created one
    		{
    		flag=1;
    		break;
    		}
    		
    	}
    	
    	if(flag==0)
       	{
       		assertTrue("The tasks with status"+t2.getStatus()+"are not getting fetched using showList", false);;
       	}
    	else if (flag==1) {
			assertTrue(true);
		} 
   }
   
   //Trying to insert a task with status Postponed and fetching it with showlist 
   @Test
   public void testShowList_with_1Postponedtask() {
   	
	TaskDTO t1 = new TaskDTO();
	flag=0;
	taskTitle="Test Task 2 in TestSHowList";
   	t1.setTitle(taskTitle);
   	t1.setStatus(TaskState.POSTPONED.toString().toUpperCase());
   	TaskDTO t2 = taskService.saveTask(t1);
   	
   	String uuid1 = t2.getUuid().toString();
   	List<TaskDTO> l1= taskService.showList();
   	System.out.println("uuid1:"+uuid1);
   	System.out.println("status should be postponed:"+t2.getStatus());
   	for(TaskDTO t: l1)
   	{
   	String uuid2=t.getUuid().toString();
   	System.out.println("uuid2:"+uuid2);
   		if(uuid1.equalsIgnoreCase(uuid2) && taskTitle.equalsIgnoreCase(t.getTitle()))//checking if the fetched task matches that with the created one
   		{
   			flag=1;
   			break;
   		}
   		
   	}
   	if(flag==1)
   	{
   		assertTrue("The tasks with status "+t2.getStatus()+" are also getting fetched using showList", false);//The postponed task should not be fetched as per the Named query findAllExcludePostponed
   	}
   	else if(flag == 0)
   	{
   		assertTrue(true);
   	}
   }
  
   //Trying to insert a task with status Restored and fetching it with showlist
   @Test
   public void testShowList_with_1Restoredtask() {
   	
	TaskDTO t1 = new TaskDTO();
	flag=0;
	taskTitle="Test Task 3 in TestSHowList";
   	t1.setTitle(taskTitle);
   	t1.setStatus(TaskState.RESTORED.toString().toUpperCase());
   	TaskDTO t2 = taskService.saveTask(t1);
   	
   	String uuid1 = t2.getUuid().toString();
   	List<TaskDTO> l1= taskService.showList();
   	System.out.println("uuid1:"+uuid1);
   	System.out.println("status should be postponed:"+t2.getStatus());
   	for(TaskDTO t: l1)
   	{
   	String uuid2=t.getUuid().toString();
   	System.out.println("uuid2:"+uuid2);
   		if(uuid1.equalsIgnoreCase(uuid2) && taskTitle.equalsIgnoreCase(t.getTitle()))//checking if the fetched task matches that with the created one
   		{
   			flag=1;
   			break;
   		}
   		
   	}
   	if(flag==0)
   	{
   		assertTrue("The tasks with status"+t2.getStatus()+"are not getting fetched using showList", false);;
   	}
	else if (flag==1) {
		assertTrue(true);
	} 
   }
  
  //Trying to insert a task with status Resolved and fetching it with showlist 
   @Test
   public void testShowList_with_1Resolvedtask() {
   	
	TaskDTO t1 = new TaskDTO();
	flag=0;
	taskTitle="Test Task 4 in TestSHowList";
   	t1.setTitle(taskTitle);
   	t1.setStatus(TaskState.RESOLVED.toString().toUpperCase());
   	TaskDTO t2 = taskService.saveTask(t1);
   	
   	String uuid1 = t2.getUuid().toString();
   	List<TaskDTO> l1= taskService.showList();
   	System.out.println("uuid1:"+uuid1);
   	System.out.println("status should be postponed:"+t2.getStatus());
   	for(TaskDTO t: l1)
   	{
   	String uuid2=t.getUuid().toString();
   	System.out.println("uuid2:"+uuid2);
   		if(uuid1.equalsIgnoreCase(uuid2) && taskTitle.equalsIgnoreCase(t.getTitle()))//checking if the fetched task matches that with the created one
   		{
   			flag=1;
   			break;
   		}
   		
   	}
   	if(flag==0)
   	{
   		assertTrue("The tasks with status"+t2.getStatus()+"are not getting fetched using showList", false);
   	}
	else if (flag==1) {
		assertTrue(true);
	} 
   }
   
   
   //Trying to find a task with UUID which is present in the DB
   @Test
   public void testFindOne_with_Valid_UUID()
   {
	   TaskDTO t1= new TaskDTO();
	   taskTitle="Test Task1 with findOne";
	   t1.setTitle(taskTitle);
	   TaskDTO t2 = taskService.saveTask(t1);
	   
	   UUID	uuid1 = t2.getUuid();
	   
	   TaskDTO returnTask = taskService.findOne(uuid1);
	   
	   String baseuuid = uuid1.toString();
	   String returnuuid= returnTask.getUuid().toString();
	   String returnTitle= returnTask.getTitle();
	   System.out.println("baseUUID:"+baseuuid+"base Title:"+taskTitle);
	   System.out.println("ReturnUUID"+returnuuid+"return title:"+returnTitle);
	   if(baseuuid.equalsIgnoreCase(returnuuid) && taskTitle.equalsIgnoreCase(returnTitle))//checking if the fetched task matches that with the created one
	   {
		   assertTrue(true);
	   }
	   else
	   {
		  assertTrue("The Element with UUID:"+baseuuid+" Cannot be found",false); 
	   }
	   
   }
   
   //Trying to find a task with UUID which is not present in the DB
   @Test
   public void testFindOne_with_InValid_UUID()
   {
	   TaskDTO t1= new TaskDTO();
	   taskTitle="Test Task2 with findOne";
	   t1.setTitle(taskTitle);
	   TaskDTO t2 = taskService.saveTask(t1);
	   
	   
	   UUID	uuid1 = UUID.randomUUID();
	   TaskDTO returnTask=null;
	   //taskTitle=taskTitle+" extra";
	   try {
	   returnTask = taskService.findOne(uuid1);
	   }
	   catch(Exception e)
	   {
		   assertTrue("Finding Record with absent UUID is not handled and exception: "+e+" is thrown",false);//Catching the exception which gets generated for absent UUID and asserting false
	   }
	  
	   finally {
	   if(returnTask==null)
	   {
		   assertTrue(true);
	   }
	   }	  
   }
  
  
  //Trying to update a task with updated date field blank 
   @Test
   public void testUpdateTask_with_blank_updatedDate()
   {
	  TaskDTO t1 = new TaskDTO();
	  taskTitle="Test Task1 with update task";
	  t1.setCreatedat(Calendar.getInstance());
	  t1.setTitle(taskTitle);
	  TaskDTO t2 = taskService.saveTask(t1);
	  TaskDTO updatedTask= taskService.updateTask(t2);
	
	  Calendar updatedDate = updatedTask.getUpdatedat();
	  
	  if(updatedDate!=null && updatedDate.getClass().getName().equalsIgnoreCase("java.util.GregorianCalendar"))//checking if date is set by the function
	  {
		assertTrue(true);  
	  }
	  else
	  {
		  assertTrue("The date is Not set",false);
	  }
   }
   
   //Trying to update a task which has an updated date
   @Test
   public void testUpdateTask_with_existing_updatedDate()
   {
   try
	{
	  TaskDTO t1 = new TaskDTO();
	  taskTitle="Test Task2 with update task";
	  t1.setCreatedat(Calendar.getInstance());
	  t1.setTitle(taskTitle);
	  
	  TaskDTO t2 = taskService.saveTask(t1);
	  t2.setUpdatedat(Calendar.getInstance());
	  Calendar initialDate=t2.getUpdatedat();
	  System.out.println("Before call:"+initialDate);
	  
	//forcing delay so that time comparison of updatedat value can be done		
	  Thread.sleep(3000);
		
	  TaskDTO updatedTask= taskService.updateTask(t2);
	
	  
	  Calendar updatedDate = updatedTask.getUpdatedat();
	  System.out.println("After Call:"+updatedDate);
	  
	 if(updatedDate!=null && (initialDate.getTimeInMillis() < updatedDate.getTimeInMillis()))//Checking if date is set and considering the Time difference to confirm the updation
	{
		assertTrue(true); 
    }
	 else
	 {
		 assertTrue("The updateTask has not updated the date",false);
	 }
	}
	
     catch (Exception e) {
		System.out.println(e);
  }
	
}
    
   
   //Trying to Resolve a task with UUID which is present in the DB
    @Test
    public void testResolveTask_with_Validuuid()
    {
    try
	  {
    	TaskDTO t1=  new TaskDTO();
    	taskTitle="Test Task1 with Resolve task";
    	t1.setTitle(taskTitle);
    	t1.setStatus(TaskState.NEW.toString().toUpperCase());
    	TaskDTO t2= taskService.saveTask(t1);
    	
    	String status_postCall="";
    	long resolvedTime,currentTime;
    	Boolean flag= taskService.resolveTask(t2.getUuid());
    	TaskDTO t3 = new TaskDTO();
    	
    	//forcing delay so that time comparison of postponedat value can be done	
    	Thread.sleep(2000);
		
    	
    	if(flag==true)
    	{
    		t3= taskService.findOne(t2.getUuid());
    		status_postCall=t3.getStatus();
    		resolvedTime=t3.getResolvedat().getTimeInMillis();
    	    currentTime=Calendar.getInstance().getTimeInMillis();
    	    
    		if(status_postCall.equalsIgnoreCase(TaskState.RESOLVED.toString()) && (resolvedTime<currentTime))//checking by using the date and status
    		{
    			assertTrue(true); 
    		}
    		else
    		{
    			assertTrue("The ResolveTask has not updated the State",false);
    		}
    	}
	  }
	  catch (Exception e) {
		System.out.println(e);
	}	
   
	}
    
    //Trying to Resolve a task with UUID which is not present in the DB
    @Test
    public void testResolveTask_with_Invaliduuid()
    {

    	
    	UUID uuid= UUID.randomUUID();
    	Boolean Statusflag=null;
    	try {
    		Statusflag= taskService.resolveTask(uuid);
    	}
    	catch(Exception e)
    	{
    		 assertTrue("Function does not handle case of absent UUID and exception: "+e+" is thrown",false);//Catching the exception which gets generated for absent UUID and asserting false
    	}
    	
    	if(Statusflag==false)
    	{
    		assertTrue(true);
    	}
    	else if(Statusflag==true)
    	{
    		assertTrue("Function shouldnt return true as the UUID passed was invalid",false);
    	}
    	
    }
  
    //Trying to Postpone a task with UUID which is present in the DB 
    @Test
    public void testPostponeTask_with_Validuuid()
    {
    	
    try
		{
    	
    	TaskDTO t1=  new TaskDTO();
    	taskTitle="Test Task1 with Postpone task";
    	t1.setTitle(taskTitle);
    	t1.setStatus(TaskState.NEW.toString().toUpperCase());
    	Integer incrementTime=100;
    	TaskDTO t2= taskService.saveTask(t1);
    	
    	String status_postCall="";
    	long postponedTime,currentTime,comparisonTime;
    	Boolean Statusflag= taskService.postponeTask(t2.getUuid(),incrementTime);
    	TaskDTO t3 = new TaskDTO();
    	
		//forcing delay so that time comparison of postponedat value can be done	
    	Thread.sleep(2000);
		
    	
    	if(Statusflag==true)
    	{
    		t3= taskService.findOne(t2.getUuid());
    		status_postCall=t3.getStatus();
    		postponedTime=t3.getPostponedat().getTimeInMillis();
    	    currentTime=Calendar.getInstance().getTimeInMillis();
    	    Calendar c = Calendar.getInstance();
    		//Creating a time reference which is 100secs more than current time
    	    c.add(Calendar.MINUTE,incrementTime);
    		comparisonTime=c.getTimeInMillis();
    	    //Using this comparision time to confirm if postponed time is updated
    		if(status_postCall.equalsIgnoreCase(TaskState.POSTPONED.toString()) && (postponedTime>currentTime && postponedTime <= comparisonTime))
    		{
    			assertTrue(true); 
    		}
    		else
    		{
    			assertTrue("The Postpone task has not updated the State",false);
    		}
    	}
    	else
    	{
    		assertTrue("The Postpone task has failed",false);
    	}
		
	}
	  catch (Exception e) {
		System.out.println(e);
   }
		
 } 
    
    //Trying to Postpone a task with UUID which is not present in the DB
    @Test
    public void testPostponeTask_with_Invaliduuid()
    {

    	
    	UUID uuid= UUID.randomUUID();
    	Boolean flag=null;
    	Integer incrementTime =100;
    	try {
    	flag= taskService.postponeTask(uuid,incrementTime);
    	}
    	catch(Exception e)
    	{
    		 assertTrue("Function Does not handle absent UUID and exception: "+e+" is thrown",false);//Catching the exception which gets generated for absent UUID and asserting false
    	}
    	
    	if(flag==false)
    	{
    		assertTrue(true);
    	}
    	else if(flag==true)
    	{
    		assertTrue("Function shouldnt return true as the UUID passed was invalid",false);
    	}
    	
    }
    
    
    //Trying to unmark postponed a task whose status is postponed
    @Test
    public void testUnmarkPostoned_with_PostponedState()
    {
     try
		{
    	
    	TaskDTO t1=new TaskDTO();
    	taskTitle="Test Task1 with unmarked Postpone task";
    	t1.setTitle(taskTitle);
    	t1.setStatus(TaskState.POSTPONED.toString().toUpperCase());
    	t1.setPostponedat(Calendar.getInstance());
		
    	TaskDTO t2= taskService.saveTask(t1);
    	UUID uuid1=t2.getUuid();
    	preChangeUUID = t2.getUuid().toString();
    	preChangeTaskTitle = taskTitle;
    	
    	
    	taskService.unmarkPostoned();
    	
    	
    	TaskDTO t3= taskService.findOne(uuid1);
    	
 
    	postChangeUUID = t3.getUuid().toString();
    	postChangeTaskTitle =t3.getTitle();
    	postChangeStatus = t3.getStatus();
    	postChangePostponedAtDate=t3.getPostponedat();
    	//validating the conditions to confirm the unmarked postpone has executed correctly
    	if(preChangeUUID.equalsIgnoreCase(postChangeUUID) && preChangeTaskTitle.equalsIgnoreCase(postChangeTaskTitle) && postChangeStatus.equalsIgnoreCase(TaskState.RESTORED.toString()) && postChangePostponedAtDate==null)
    	{
    		assertTrue(true);
    	}
    	else
    	{
    		assertTrue("The Unmark postponed failed to unmark a postponed task",false);
    	}

	}
	catch (Exception e) {
			System.out.println(e);
	}	
    	
  }
    
    //Trying to unmark postponed a task whose status is New
    @Test
    public void testUnmarkPostoned_with_NewState()
    {
     try
		{
    	
    	TaskDTO t1=new TaskDTO();
    	taskTitle="Test Task2 with unmarked Postpone task";
    	t1.setTitle(taskTitle);
    	t1.setStatus(TaskState.NEW.toString().toUpperCase());
    	t1.setPostponedat(Calendar.getInstance());
    	
		
    	TaskDTO t2= taskService.saveTask(t1);
    	UUID uuid1=t2.getUuid();
    	preChangeUUID = t2.getUuid().toString();
    	preChangeTaskTitle = taskTitle;
    	preChangeStatus = t2.getStatus();
    	preChangePostponedAt=t2.getPostponedat().getTimeInMillis();
    	
    	taskService.unmarkPostoned();
    	
    	
    	TaskDTO t3= taskService.findOne(uuid1);
    	
 
    	postChangeUUID = t3.getUuid().toString();
    	postChangeTaskTitle =t3.getTitle();
    	postChangeStatus = t3.getStatus();
    	postChangePostponedAt=t3.getPostponedat().getTimeInMillis();
    	//validating the conditions to confirm the unmarked postpone has executed correctly
    	if(preChangeUUID.equalsIgnoreCase(postChangeUUID) && preChangeTaskTitle.equalsIgnoreCase(postChangeTaskTitle) && postChangeStatus.equalsIgnoreCase(preChangeStatus) && preChangePostponedAt==postChangePostponedAt)
    	{
    		assertTrue(true);
    	}
    	else
    	{
    		assertTrue("The Unmark postponed has unmarked a task with status NEW",false);
    	}

	}
	  catch (Exception e) {
			System.out.println(e);
   }	
    	
 }
    
    //Trying to unmark postponed a task whose status is Restored
    @Test
    public void testUnmarkPostoned_with_RestoredState()
    {
    try
		{
    	TaskDTO t1=new TaskDTO();
    	taskTitle="Test Task3 with unmarked Postpone task";
    	t1.setTitle(taskTitle);
    	t1.setStatus(TaskState.RESTORED.toString().toUpperCase());
    	t1.setPostponedat(Calendar.getInstance());
    	
    	TaskDTO t2= taskService.saveTask(t1);
    	UUID uuid1=t2.getUuid();
    	preChangeUUID = t2.getUuid().toString();
    	preChangeTaskTitle = taskTitle;
    	preChangeStatus = t2.getStatus();
    	preChangePostponedAt=t2.getPostponedat().getTimeInMillis();
    	
    	taskService.unmarkPostoned();
    	
    	
    	TaskDTO t3= taskService.findOne(uuid1);
    	
 
    	postChangeUUID = t3.getUuid().toString();
    	postChangeTaskTitle =t3.getTitle();
    	postChangeStatus = t3.getStatus();
    	postChangePostponedAt=t3.getPostponedat().getTimeInMillis();
    	//validating the conditions to confirm the unmarked postpone has executed correctly
    	if(preChangeUUID.equalsIgnoreCase(postChangeUUID) && preChangeTaskTitle.equalsIgnoreCase(postChangeTaskTitle) && postChangeStatus.equalsIgnoreCase(preChangeStatus) && preChangePostponedAt==postChangePostponedAt)
    	{
    		assertTrue(true);
    	}
    	else
    	{
    		assertTrue("The Unmark postponed has unmarked a task with status Restored",false);
    	}
	}
	 catch (Exception e) {
		System.out.println(e);
	}
    	
  }
    
    //Trying to unmark postponed a task whose status is Resolved
    @Test
    public void testUnmarkPostoned_with_ResolvedState()
    {
    	try
		{
    	
    	TaskDTO t1=new TaskDTO();
    	taskTitle="Test Task4 with unmarked Postpone task";
    	t1.setTitle(taskTitle);
    	t1.setStatus(TaskState.RESOLVED.toString().toUpperCase());
    	t1.setPostponedat(Calendar.getInstance());
    	
		
    	TaskDTO t2= taskService.saveTask(t1);
    	UUID uuid1=t2.getUuid();
    	preChangeUUID = t2.getUuid().toString();
    	preChangeTaskTitle = taskTitle;
    	preChangeStatus = t2.getStatus();
    	preChangePostponedAt=t2.getPostponedat().getTimeInMillis();
    	
    	taskService.unmarkPostoned();
    	
    	TaskDTO t3= taskService.findOne(uuid1);
    	
    	postChangeUUID = t3.getUuid().toString();
    	postChangeTaskTitle =t3.getTitle();
    	postChangeStatus = t3.getStatus();
    	postChangePostponedAt=t3.getPostponedat().getTimeInMillis();
    	//validating the conditions to confirm the unmarked postpone has executed correctly
    	if(preChangeUUID.equalsIgnoreCase(postChangeUUID) && preChangeTaskTitle.equalsIgnoreCase(postChangeTaskTitle) && postChangeStatus.equalsIgnoreCase(preChangeStatus) && preChangePostponedAt==postChangePostponedAt)
    	{
    		assertTrue(true);
    	}
    	else
    	{
    		assertTrue("The Unmark postponed has unmarked a task with status Restored",false);
    	}

	}
	   catch (Exception e) {
			System.out.println(e);
    }	
  }
   
    //Trying to unmark postponed 2 tasks with different Statuses
    @Test
    public void testUnmarkPostoned_with_TwodifferentTasks()
    {
     try
	  {
    	
    	TaskDTO t1=new TaskDTO();
    	taskTitle="Test Task5 with unmarked Postpone task";
    	t1.setTitle(taskTitle);
    	t1.setStatus(TaskState.POSTPONED.toString().toUpperCase());
    	t1.setPostponedat(Calendar.getInstance());
    	
    	
    	TaskDTO t2= taskService.saveTask(t1);
    	UUID uuid1=t2.getUuid();
    	preChangeUUID = t2.getUuid().toString();
    	preChangeTaskTitle = taskTitle;
    	
    	
    	
    	
    	TaskDTO t3=new TaskDTO();
    	String taskTitle2="Test Task6 with unmarked Postpone task";
    	t3.setTitle(taskTitle2);
    	t3.setStatus(TaskState.NEW.toString().toUpperCase());
    	t3.setPostponedat(Calendar.getInstance());
    	
		
    	TaskDTO t4= taskService.saveTask(t3);
    	UUID uuid2=t4.getUuid();
    	String preChangeUUID2 = t4.getUuid().toString();
    	String preChangeTaskTitle2 = taskTitle2;
    	String preChangeStatus2 = t4.getStatus();
    	long preChangePostponedAt2=t4.getPostponedat().getTimeInMillis();
    	
    	
    	
    	taskService.unmarkPostoned();

    	TaskDTO t5= taskService.findOne(uuid1);
    	TaskDTO t6= taskService.findOne(uuid2);
    	
    	
    	
    	postChangeUUID = t5.getUuid().toString();
    	postChangeTaskTitle =t5.getTitle();
    	postChangeStatus = t5.getStatus();
    	postChangePostponedAtDate=t5.getPostponedat();
    	
    	String postChangeUUID2 = t6.getUuid().toString();
    	String postChangeTaskTitle2 =t6.getTitle();
    	String postChangeStatus2 = t6.getStatus();
    	long postChangePostponedAt2=t6.getPostponedat().getTimeInMillis();
    	
    	
    	
    	if(preChangeUUID.equalsIgnoreCase(postChangeUUID) && preChangeTaskTitle.equalsIgnoreCase(postChangeTaskTitle) && postChangeStatus.equalsIgnoreCase(TaskState.RESTORED.toString()) && postChangePostponedAtDate==null)
    	{
    		assertTrue(true);
    	}
    	else
    	{
    		assertTrue("The Unmark postponed failed to unmark a postponed task",false);
    	}
    	
    	if(preChangeUUID2.equalsIgnoreCase(postChangeUUID2) && preChangeTaskTitle2.equalsIgnoreCase(postChangeTaskTitle2) && postChangeStatus2.equalsIgnoreCase(preChangeStatus2) && preChangePostponedAt2==postChangePostponedAt2)
    	{
    		assertTrue(true);
    	}
    	else
    	{
    		assertTrue("The Unmark postponed has unmarked a task with status NEW",false);
    	}
    	
	}
		catch (Exception e) {
			System.out.println(e);
		}   
	  
  }
    
    //Trying to run Unmark postponed twice to check for no null pointer exception
    @Test
    public void testUnmarkPostoned_Run_Twice()
    {
    try
		{
    	TaskDTO t1=new TaskDTO();
    	taskTitle="Test Task7 with unmarked Postpone task";
    	t1.setTitle(taskTitle);
    	t1.setStatus(TaskState.POSTPONED.toString().toUpperCase());
    	t1.setPostponedat(Calendar.getInstance());
    	
    	
		
    	TaskDTO t2= taskService.saveTask(t1);
    	UUID uuid1=t2.getUuid();
    	
    	
    	taskService.unmarkPostoned();
    	
		taskService.unmarkPostoned();//trying to check if calling again doesn't throw any null pointer exception
		assertTrue(true);//assert true if no exceptions are caught
		}
		catch (Exception e) {
			 assertTrue("Exception when unmarked postponed is called again: "+e+" is thrown",false);
		}
    	
    	
       
    }
    
    @EnableJpaRepositories
    @Configuration
    @SpringBootApplication
    public static class EndpointsMain{}
}
