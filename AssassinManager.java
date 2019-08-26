// Yiran Jia
// 4/19/18
// CSE143
// TA: JASON WAATAJA 
// Assignment 3: Assassin Manager
//
// This programs manages a game of assassin by controling a kill-ring, or a list of alive player(s);
// and a graveyard, or a list of player(s) who were killed. Once the player in the kill-ring was 
// killed by the person who was stalking him/her, the program will remove this player from the
// kill-ring to the graveyard. 

import java.util.*;
public class AssassinManager {
   private AssassinNode frontKill;
   private AssassinNode frontGrave;
   
   // pre: this list is not empty (throw an IllegalArgumentException if not)
   // post: construct an empty graveyard list and a kill-ring list that includes all the 
   // names in this string list
      public AssassinManager(List<String> names) {
      if (names.size() == 0) {
         throw new IllegalArgumentException();
      } else {
         frontGrave = null;
         frontKill = new AssassinNode(names.get(0));
         AssassinNode currKill = frontKill;
         for (int i = 1; i < names.size(); i++) { 
            while (currKill.next != null) {
               currKill = currKill.next;
            }
            currKill.next = new AssassinNode(names.get(i));
         }
      }
   }
   
   // post: return this word with all-lower-case letters
   private String lower(String word) {
      return word.toLowerCase();
   }
       
   // post: show the stalking relatoinships in the kill-ring by printing who is stalling who
   public void printKillRing() {
      AssassinNode currKill = frontKill;
      while (currKill.next != null) {
         System.out.println("    " + currKill.name + " is stalking " + currKill.next.name);
         currKill = currKill.next;
      }
      System.out.println("    " + currKill.name + " is stalking " + frontKill.name);
   }
   
   // pre: produce no output if the graveyard is empty
   // post: show the murder relationships in the graveyard by printing who was killed by who  
   public void printGraveyard() {
      AssassinNode currGrave = frontGrave;
      while (currGrave != null) {
         System.out.println("    " + currGrave.name + " was killed by " + currGrave.killer);
         currGrave = currGrave.next;
      }
   }
   
   // pre: ignore case in compairng names 
   // post: return true if the given name is in this list, otherwise false
   private boolean listContains(String name, AssassinNode list) {
      AssassinNode currAll = list;
      while (currAll != null) {
         if (lower(currAll.name).equals(lower(name))) {
            return true;
         }
         currAll = currAll.next;
      }
      return false;
   }
      
   // pre: ignore case in compairng names 
   // post: return if the given name is in the kill-ring
   public boolean killRingContains(String name) {
      return listContains(name, frontKill);
   }
   
   // pre: ignore case in compairng names 
   // post: return if the given name is in the graveyard
   public boolean graveyardContains(String name) {
      return listContains(name, frontGrave);
   }
  
   // post: return if there is only one person in the kill-ring
   public boolean gameOver() {
      return frontKill.next == null;
   }
   
   // pre: the game is over (return null if not)
   // post: return the name of the winner in this game
   public String winner() {
      if (frontKill.next != null) {
         return null;
      } else {
         return frontKill.name;
      }
   }
   
   // pre: ignore case in comparing names
   // the given name in current kill-ring (throw an IllegalArgumentException if not)
   // the game is not over (throw an IllegalStateException if not)
   // post: transfer this person who was just killed from kill-ring list to graveyard list
   public void kill(String name) {
      String decision = lower(name);
      if (!killRingContains(decision)) {
         throw new IllegalArgumentException();
      }
      if (gameOver()) {
         throw new IllegalStateException();
      }
     
      AssassinNode currKill = frontKill;
      AssassinNode currGrave = frontGrave;
      AssassinNode temp1 = frontKill; 
      if (lower(frontKill.name).equals(decision)) {
         frontKill = frontKill.next; 
         while (currKill.next != null) { 
            currKill = currKill.next;
         }
      } else {
         while (!lower(currKill.next.name).equals(decision)) {
            currKill = currKill.next;
         }
         temp1 = currKill.next; 
         currKill.next = currKill.next.next; 
      }  
      
      if (frontGrave == null) {
         frontGrave = temp1;
      } else {
         frontGrave = temp1;
         frontGrave.next = currGrave;
      }
      frontGrave.killer = currKill.name;  
   }
}       
         
         
         

 
