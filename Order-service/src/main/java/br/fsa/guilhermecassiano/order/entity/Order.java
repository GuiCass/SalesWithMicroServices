package br.fsa.guilhermecassiano.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordem")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String data;
    private Double valorTotal;

    // Relacionamento com o usuário
    @Column(name = "usuario_id")
    private Long usuarioId;
}
