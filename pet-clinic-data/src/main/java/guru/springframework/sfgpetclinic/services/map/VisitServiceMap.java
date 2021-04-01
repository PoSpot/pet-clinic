package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class VisitServiceMap extends BaseMapService<Visit> implements VisitService {

    // John's impl below, but not sure why we need those.
    // (fields: date, descr, pet will be checked later in validation, I guess).
    // same for the fields' fields.
//        @Override
//        public Visit save(Visit visit) {
//
//            if(visit.getPet() == null || visit.getPet().getOwner() == null || visit.getPet().getId() == null
//                    || visit.getPet().getOwner().getId() == null){
//                throw new RuntimeException("Invalid Visit");
//            }
//
//            return super.save(visit);
//        }
}
