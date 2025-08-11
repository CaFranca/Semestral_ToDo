package br.edu.ifsp.spo.todolist.services;

import br.edu.ifsp.spo.todolist.models.User;
import br.edu.ifsp.spo.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Buscando usuário para login: " + username);

        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isPresent()) {
            User u = userOpt.get();
        } else {
            System.out.println("Usuário não encontrado por email, tentando pelo nome...");
            userOpt = userRepository.findByName(username);
            if (userOpt.isPresent()) {
                User u = userOpt.get();
                System.out.println("Usuário encontrado por nome: " + u.getName());
            }
        }

        return userOpt.orElseThrow(() ->
                new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

}
