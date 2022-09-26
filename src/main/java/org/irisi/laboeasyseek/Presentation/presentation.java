package org.irisi.laboeasyseek.Presentation;


import org.irisi.laboeasyseek.entities.Post;
import org.irisi.laboeasyseek.services.PostService;

public class presentation {

    public static void main(String[] args)  {

        PostService postService = new PostService();

        Post post = new Post();
        post.setName("first post");
        Post p = postService.save(post);
//
//        List<Compte> comptes = sm.findAll();
//        comptes.forEach(compte -> System.out.println(compte.toString( ))
//        );
//
////        Compte cp = sm.findByCode(112L);
////        System.out.println("compte : "+cp.toString());
//
////        sm.delete(112L);
//        Compte cp = sm.findByCode(113L);
//        System.out.println(cp.toString());
//        cp.setSolde(78500.30);
//        Compte cp1 = sm.update(cp);
//        System.out.println(cp1.toString());

    }
}
