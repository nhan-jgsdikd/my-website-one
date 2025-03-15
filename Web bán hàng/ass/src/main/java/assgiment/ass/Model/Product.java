package assgiment.ass.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "nvarchar(max)")
    private String nameproduct;

    @Column(columnDefinition = "nvarchar(max)")
    private String photo;

    private double price;  

    @Column(columnDefinition = "nvarchar(max)")
    private String description;
}
